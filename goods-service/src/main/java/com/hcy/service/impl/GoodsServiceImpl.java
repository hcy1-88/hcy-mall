package com.hcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hcy.dto.GoodsForDetail;
import com.hcy.entity.*;
import com.hcy.mapper.*;
import com.hcy.pojo.R;
import com.hcy.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcy.util.MinioUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hcy
 * @since 2021-10-27
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private PreviewImgMapper previewImgMapper;
    @Autowired
    private DetailImgMapper detailImgMapper;
    @Autowired
    private PresaleMapper presaleMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private FullReduceMapper fullReduceMapper;

    @Autowired
    private MinioUtil minioUtil;
    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.port}")
    private Integer PORT;
    @Value("${minio.secure}")
    private Boolean SECURE;
    @Value("${minio.folder}")
    private String FOLDER;

    public List<Goods> getAllGoods(){
        return goodsMapper.getAllGoods();
    }

    @Override
    public String insertGoods(Goods goods) {
        this.baseMapper.insert(goods);
        return "ok";
    }

    @Override
    public R updateGoodsById(Goods goods) {
        this.baseMapper.updateById(goods);
        return new R(1001,true,null);
    }

    @Override
    public R deleteGoodsById(String goodsId) {
        this.baseMapper.deleteById(goodsId);
        return new R(1001,true,null);
    }

    @Override
    public String updateGoodsImg(MultipartFile mf, String goodsId) {
        // 用户上传文件的初始文件名
        String oldFilename = mf.getOriginalFilename();
        // 扩展名
        String extension = "."+ FilenameUtils.getExtension(oldFilename);  // .txt
        String name = FilenameUtils.getBaseName(oldFilename);
        // 新的文件名
        String newFilename = name + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-","")+extension;
        String objName = FOLDER + "/" + newFilename;
        // 上传图片到minIO
        minioUtil.putObject(BUCKET_NAME,mf,objName);
        String newImgUrl =getImgUrl(newFilename);
        // 修改商品图片的URL，如果原先商品有图片，则要删除图片
        String oldImgUrl = goodsMapper.findImgUrlByGoodsId(goodsId);
        System.out.println("旧的地址："+oldImgUrl);
        if (!"".equals(oldImgUrl.trim()) && oldImgUrl != null){
            // 删除原先的图片
            minioUtil.removeObject(BUCKET_NAME,getObjName(oldImgUrl));
        }
        Goods goods = new Goods();
        goods.setId(goodsId);
        goods.setImgUrl(newImgUrl);
        this.baseMapper.updateById(goods);
        return "ok";
    }

    @Override
    @Transactional
    public List<GoodsForDetail> findDetailByGoodsId(String goodsId) {
        // 查询商品基本信息和sku
        List<Sku> skuByGoodsId = this.skuMapper.findSkuByGoodsId(goodsId);
        // 查找预览图片的地址
        QueryWrapper<PreviewGoodsImg> previewGoodsImgQueryWrapper = new QueryWrapper<>();
        previewGoodsImgQueryWrapper.eq("goods_id",goodsId);
        List<PreviewGoodsImg> previewGoodsImgs = this.previewImgMapper.selectList(previewGoodsImgQueryWrapper);
        List<String> previews = new ArrayList<>();
        previewGoodsImgs.forEach(item -> {
            previews.add(item.getImgUrl());
        });
        // 查找商品详情图片
        QueryWrapper<DetailGoodsImg> detailGoodsImgQueryWrapper = new QueryWrapper<>();
        detailGoodsImgQueryWrapper.eq("goods_id", goodsId);
        List<DetailGoodsImg> detailGoodsImgs = this.detailImgMapper.selectList(detailGoodsImgQueryWrapper);
        List<String> details = new ArrayList<>();
        detailGoodsImgs.forEach(item -> {
            details.add(item.getImgUrl());
        });
        // 1,查找是不是预售
        QueryWrapper<Presale> presaleQueryWrapper = new QueryWrapper<>();
        presaleQueryWrapper.eq("goods_id",goodsId);
        Presale presale = this.presaleMapper.selectOne(presaleQueryWrapper);

        // 2,查找是不是优惠券促销
        QueryWrapper<Coupon> cqw = new QueryWrapper<Coupon>().eq("goods_id", goodsId).eq("enable",1);
        Coupon coupon = this.couponMapper.selectOne(cqw);
        //3，查找是不是满减促销
        QueryWrapper<FullReduce> fqw = new QueryWrapper<FullReduce>().eq("goods_id", goodsId).eq("enable",1);
        List<FullReduce> fullReduces = this.fullReduceMapper.selectList(fqw);
        // 4，查找是不是多件打折
        QueryWrapper<Discount> dqw = new QueryWrapper<Discount>().eq("goods_id", goodsId).eq("enable",1);
        List<Discount> discounts = this.discountMapper.selectList(dqw);

        // 赋值工作
        List<GoodsForDetail> goodsForDetails = new ArrayList<GoodsForDetail>();
        skuByGoodsId.forEach(sku -> {
            GoodsForDetail goodsForDetail = new GoodsForDetail();
            goodsForDetail.setSkuId(sku.getSkuId());
            goodsForDetail.setGoodsId(sku.getGoodsId());
            goodsForDetail.setGoodsName(sku.getGoods().getGoodsName());
            goodsForDetail.setSku(sku.getSku());
            goodsForDetail.setStock(sku.getStock());
            goodsForDetail.setOriginalPrice(sku.getGoods().getOriginalPrice().toPlainString());
            goodsForDetail.setNowPrice(sku.getGoods().getNowPrice().toPlainString());
            goodsForDetail.setSellerId(sku.getSellerId());
            goodsForDetail.setPreviewImgs(previews);
            goodsForDetail.setDetailImgs(details);
            goodsForDetail.setPresale(presale);
            goodsForDetail.setCoupons(coupon);
            goodsForDetail.setFullReduces(fullReduces);
            goodsForDetail.setDiscounts(discounts);
            StringBuilder sb = new StringBuilder();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (presale != null){
                Date expireTime = presale.getExpireTime();
                sb.append("该商品为预售商品，发货时间为：").append(dateFormat.format(expireTime)).append("之后；");
            }
            if (coupon != null){
                sb.append("可以使用"+coupon.getMoneySize()+"元优惠券呦！");
                sb.append("时间限制：");
                sb.append(dateFormat.format(coupon.getStartTime()));
                sb.append(" 到 "+dateFormat.format(coupon.getExpireTime()));

            }
            if (fullReduces.size() != 0 && fullReduces != null){
                sb.append("满减促销，");
                fullReduces.forEach( f -> {
                    sb.append("满"+f.getFull()+"元，立减"+f.getReduce()+"元；");
                });
                sb.append("时间限制：");
                sb.append(dateFormat.format(fullReduces.get(0).getStartTime()));
                sb.append(" 到 "+dateFormat.format(fullReduces.get(0).getExpireTime()));
            }
            if (discounts.size() != 0 && discounts != null){
                sb.append("多件打折，");
                discounts.forEach(d -> {
                    sb.append("满"+d.getNum()+"件，打"+d.getDiscount()+"折；");
                });
                sb.append("时间限制：");
                sb.append(dateFormat.format(discounts.get(0).getStartTime()));
                sb.append(" 到 "+dateFormat.format(discounts.get(0).getExpireTime()));
            }
            goodsForDetail.setPromotionWay(sb.toString());
            goodsForDetails.add(goodsForDetail);
        });
        return goodsForDetails;
    }

    // 获取图片的url路径
    public String getImgUrl(String objName){
        String http = SECURE==true?"https":"http";
        // http://192.168.1.131:9000/hcymall/goodsImg/我爱你202105061215139999.jpg
        return http+"://"+ENDPOINT+":"+PORT+"/"+BUCKET_NAME+"/"+FOLDER+"/"+objName;
    }
    public String getObjName(String imgUrl){
        // 从 url 中获取图片的objectName
        int x = imgUrl.indexOf(BUCKET_NAME);
        int wo = x + BUCKET_NAME.length() + 1;
        String f = imgUrl.substring(wo);
        return f;
    }
}
