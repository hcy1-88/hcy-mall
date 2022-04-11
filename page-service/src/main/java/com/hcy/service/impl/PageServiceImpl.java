package com.hcy.service.impl;

import com.hcy.dto.GoodsForDetail;
import com.hcy.feign.GoodsFeign;
import com.hcy.feign.SkuFeign;
import com.hcy.service.PageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 20:08
 */
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private SkuFeign skuFeign;
    @Override
    public String genPage(String goodsId) throws IOException, TemplateException {

        //1.构建Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //2.设置模板文件目录(Directory)
        configuration.setDirectoryForTemplateLoading(new File("F:/idea_workspace/projects/my-mall/hcy-mall/page-service/src/main/resources/templates"));
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.获取模板对象
        Template template = configuration.getTemplate("final_detail.ftl");
        //5.创建模型数据
        List<GoodsForDetail> detailByGoodsId = skuFeign.getDetailByGoodsId(goodsId);
        Map data = new HashMap(){{
            put("goodsForDetails",detailByGoodsId);         // 将 模板中的变量作为 key
        }};
        //6.创建输出流(Writer)对象
        Writer out = new FileWriter("F:/my_projects/hcymall/openresty-1.19.9.1-win64/page/"+goodsId+".html");  // 输出路径，会生成 demo.html
        //7.输出
        template.process(data,out);
        //8.关闭
        out.close();
        return "ok";
    }
}
