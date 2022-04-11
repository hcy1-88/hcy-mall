<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>详情页面</title>
		<link href="./css/shopdetail.css" rel="stylesheet" type="text/css">
		<script src="./js/jquery-3.6.0.min.js"></script>
		
		<script src="./js/vue.global.js"></script>
		<script src="./js/cookieManager.min.js"></script>
		<script src="./js/common.js"></script>
		<script src="./js/axios.min.js"></script>
		
	</head>

	<body>
		<div id="app">
			<!-----header部分------->
			<div class="header">
				<div class="top">
					<div class="top1"  v-if="logined">
						<a href="javascript:void(0);">你好，{{currentUser.name}}！</a>
						<a href="javascript:void(0);"><img src="images/index_img/top1.jpg">我的购物车</a>
						<a href="javascript:void(0);"><img src="images/index_img/top3.jpg">联系我们</a>
					</div>
					<div class="top1" v-else>
						<a href="" @click.prevent="toLoginPage">登录</a>
						<a href="" @click.prevent="toRegPage">注册</a>
						<a href="javascript:void(0);"><img src="images/index_img/top1.jpg">我的购物车</a>
						<a href="javascript:void(0);"><img src="images/index_img/top3.jpg">联系我们</a>
					</div>
					
				</div>
				<!-----logo_search部分------->
				<div class="logobg">
					<div class="center">
						<div class="logo">
							<img src="images/index_img/logo.gif" width="249" height="55">
						</div>
						<form id="searchForm">
							<input type="text" id="searchTxt">
							<input type="submit" value="搜  索" id="search_btn">
						</form>
					</div>
				</div>
				<!-----主导航部分------->
				<div class="bottom">
					<div class="menu"><a href="javascript:void(0);">全部商品分类</a></div>
					<div class="nav">
						<a href="index.html" class="now">首页</a>
						<a href="tuangou.html">团购促销</a>
						<a href="mingshihuicui.html">名师荟萃</a>
						<a href="yipinyizhan.html">艺品驿站</a>
						<a href="western.html">欧式摆件</a>
					</div>
				</div>


			</div>
			<!-----header结束------->
			<!-----商品详情部分------->
			<div class="shopdetails" v-if="this.dataStatus">
				<!-------放大镜-------->
				<div id="leftbox" >
					<div id="showbox">
						<!-- <template v-for="(item,index) in this.goodsForDetail[0].previewImgs">
							<img v-if="this.showImgs" :src=item width="400" height="550" />
						</template> -->
						<img v-for="item in this.goodsForDetail[0].previewImgs" :src=item width="400" height="550" />
						<!-- <img src="images/shopdetail/img01.png" width="400" height="550" />
						  <img src="images/shopdetail/img02.png" width="400" height="550" />
						  <img src="images/shopdetail/img03.png" width="400" height="550" />
						  <img src="images/shopdetail/img04.png" width="400" height="550" />
						  <img src="images/shopdetail/img05.png" width="400" height="400" />
						  <img src="images/shopdetail/img01.png" width="400" height="400" /> -->
					</div>
					<!--展示图片盒子-->
					<div id="showsum"></div>
					<!--展示图片里边-->
					<p class="showpage">
						<a href="javascript:void(0);" id="showlast">
							< </a>
								<a href="javascript:void(0);" id="shownext"> > </a>
					</p>

				</div>
				<!----中间----->

				<div class="centerbox">
					<p class="imgname">{{this.goodsForDetail[0].goodsName}}</p>
					<p class="Aprice">价格：<samp>￥{{this.goodsForDetail[0].originalPrice}}</samp></p>
					<p class="price" v-if="this.goodsForDetail[0].presale != null">预售价：<samp>￥{{this.goodsForDetail[0].nowPrice}}</samp></p>
					<p class="price" v-if="this.goodsForDetail[0].presale != null">定金：<samp>￥{{this.goodsForDetail[0].presale.presalePrice}}</samp></p>
					<p class="price" v-else>现价：<samp>￥{{this.goodsForDetail[0].nowPrice}}</samp></p>
					<p class="youhui">店铺促销：<samp>{{this.goodsForDetail[0].promotionWay}}</samp></p>
					<p class="kefu">客服：</p>
					
					<ul v-for="skuKey in skuKeys">
						<li class="kuanshi">{{skuKey}}：</li>
						<template v-for="(vv,index) in skuObj[skuKey]">
							<li class="now shopimg" v-if="index == 0 && vv != null" @click="changeStock(skuKey,vv)" ><a href="javascript:void(0);" >{{vv}}</a></li>
							<li class="shopimg" v-if="index != 0 && vv != null" @click="changeStock(skuKey,vv)"><a href="javascript:void(0);" >{{vv}}</a></li>
						</template>
						
						<br>
					</ul>
					
					
					
					<div class="clear"></div>
					<!-- <p class="chima">尺码：</p> -->
					<p class="stock">库存：&emsp;&emsp;{{this.currStock}}</p>
					<p class="stock">数量：&emsp;&emsp;
						<button style="width: 20px;" @click="reduceNum">-</button>&nbsp;
						<input style="width: 20px;" v-model="num" @blur="handleNum" onkeyup="this.value=this.value.replace(/\D|^0/g,'')" onafterpaste="this.value=this.value.replace(/\D|^0/g,'')"></input>
						&nbsp;<button style="width: 20px;" @click="addNum">+</button>
						
					</p>
					<p class="buy"><a href="javascript:void(0);" id="firstbuy" @click="buy">立即购买</a><a
							href="javascript:void(0);">加入购物车</a></p>
					<div class="clear"></div>
					<div class="fenx"><a href="javascript:void(0);"><img src="images/shopdetail/tell07.png"></a></div>
					<p class="fuwu">服务承诺：</p>
					<p class="pay">支付方式：</p>
				</div>

				<!-----右边------->
				<div class="rightbox">
					<p class="name">——热卖商品</p>
					<img src="images/shopdetail/reimai02.jpg" width="130" height="180">
					<p>￥58元</p>

					<img src="images/shopdetail/reimai01.jpg" width="130" height="180">
					<p>￥58元</p>


					<img src="images/shopdetail/reimai03.jpg" width="130" height="180">
					<p>￥58元</p>
				</div>

			</div>
			<!-----商品详情部分结束------->
			<!-----商品详情评价部分------->
			<div class="evaluate">

				<div class="classify">
					<div class="shopim">
						<p class="name">青蛙工艺家居<img src="images/shopdetail/tell01.png" width="22" height="22"></p>
						<img src="images/shopdetail/tellbottom.png">
						<p class="sc"><a href="#">收藏店铺</a></p>
						<p class="sc"><a href="#">进入店铺</a></p>
						<div class="search">
							<input class="left" type="text" />
							<input class="right" type="button" style=" cursor:pointer;" value="" />
						</div>
					</div>
					<div class="shopfl">
						<p class="name">本店分类</p>
						<ul>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">全部商品</a></li>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">木质商品</a></li>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">石制商品</a></li>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">陶制商品</a></li>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">家居厨房</a></li>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">欧式混搭</a></li>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">桌面摆件</a></li>
							<li><a href="#"><img src="images/shopdetail/tell02.png" width="10" height="10">书香文房</a></li>
						</ul>
					</div>
					<div class="shopsee">
						<p class="name">看了又看</p>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see.jpg" width="170" height="245">
							<p>手绘陶瓷茶壶</p>
							<p>商城价:168元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see1.jpg" width="170" height="245">
							<p>茶具特价紫砂</p>
							<p>商城价:￥234元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see2.jpg" width="170" height="245">
							<p>创意爱家杯盖</p>
							<p>商城价:￥38元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see03.png" width="170" height="245">
							<p>木质棋盘</p>
							<p>商城价:￥158元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see04.png" width="170" height="245">
							<p>北欧风创意椅子</p>
							<p>商城价:￥178元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see5.jpg" width="170" height="245">
							<p>木质简约衣架</p>
							<p>商城价:￥68元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see07.png" width="170" height="245">
							<p>龙猫微观景观小夜灯</p>
							<p>商城价:￥258元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see06.png" width="170" height="245">
							<p>火树银花灯</p>
							<p>商城价:￥858元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/content_04.jpg" width="170" height="245">
							<p>木质烘脚器</p>
							<p>商城价:￥108元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see08.png" width="170" height="245">
							<p>个性实木壁灯</p>
							<p>商城价:￥68元</p>
						</a>
						<a href="#" class="ex01">
							<img src="images/shopdetail/see09.png" width="170" height="245">
							<p>创意马克杯</p>
							<p>商城价:￥58元</p>
						</a>

					</div>


				</div>


				<div class="tabbedPanels">
					<ul class="tabs">
						<li><a href="#panel01">商品详情</a></li>
						<li><a href="#panel02" class="active">累计评价</a></li>
						<li><a href="#panel03">商品推荐</a></li>
					</ul>

					<div class="panelContainer">
						<div class="panel" id="panel01" v-if="this.dataStatus">
							<p class="sell">商品描述</p>
							<p>创意造型 浓浓文艺气息 闲暇时光 与好友分享</p>
							<p></p>
							<p class="sell">整体款式</p>
							<img v-for="item in this.goodsForDetail[0].detailImgs" :src=item>
							<div class="clear"></div>
						</div>

						<div class="panel" id="panel02">
							<p class="sell">买家评价</p>
							<img src="images/shopdetail/detail101.png">
							<p class="judge">全部评价(20)<span>晒图(13)</span></p>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">落***1</p>
									<p>杯子很可爱！就是有两三个杯子后面的小图案有一丢丢斜下来，不过没多大关系，其他的还好。有一点真的特别特别好的就是😂包裹的非常非常非常严实，简直就是里三层外三层！杯子完好无损，赠送的刷子也包装的很好😂让我第一眼以为那是一个棉花糖hhh
									</p>
									<p class="which">颜色:创意胡子</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail105.jpg" width="40px" height="40px">

								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">席***2</p>
									<p>质量很好，快递也很快，拆包裹很艰难～～～包得太好了，没有碎。厚度也可以。值得购买！</p>
									<p class="which">颜色:熊猫套装</p>
									<img src="images/shopdetail/detail106.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail107.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">怒***4</p>
									<p>特别好，必须赞👍，质量好，很漂亮，快递也好快的。还挺优惠。看图吧。</p>
									<p class="which">颜色:熊猫套装</p>
									<img src="images/shopdetail/detail108.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail109.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail110.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">毛***字</p>
									<p>很精致，用起来很满意，做工也非常的细致，用着很满意哦，非常值得购买</p>
									<p class="which">颜色:创意胡子</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">轻***4</p>
									<p>店家服务太贴心了，没有破碎，包装非常严实</p>
									<p class="which">颜色:铁塔套装</p>
									<img src="images/shopdetail/detail106.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail107.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">里***2</p>
									<p>不错，很可爱包装很好，赶快下手吧</p>
									<p class="which">颜色:四色小猫</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">啥***2</p>
									<p>一直想要咖啡杯，这次总算拿到手了。很不错的陶瓷杯。图案也很可爱。实物与图一样。</p>
									<p class="which">颜色:熊猫套装</p>
									<img src="images/shopdetail/detail108.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail110.jpg" width="40px" height="40px">
								</div>
							</div>



							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">胡***2</p>
									<p>宝贝很实惠 拆包装的时候很惊讶 竟然包了四层！完好无损</p>
									<p class="which">颜色:四色小猫</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">落***1</p>
									<p>杯子很可爱！就是有两三个杯子后面的小图案有一丢丢斜下来，不过没多大关系，其他的还好。有一点真的特别特别好的就是😂包裹的非常非常非常严实，简直就是里三层外三层！杯子完好无损，赠送的刷子也包装的很好😂让我第一眼以为那是一个棉花糖hhh
									</p>
									<p class="which">颜色:创意胡子</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail105.jpg" width="40px" height="40px">

								</div>
							</div>
							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">轻***4</p>
									<p>店家服务太贴心了，没有破碎，包装非常严实</p>
									<p class="which">颜色:铁塔套装</p>
									<img src="images/shopdetail/detail106.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail107.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">里***2</p>
									<p>不错，很可爱包装很好，赶快下手吧</p>
									<p class="which">颜色:四色小猫</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
								</div>
							</div>
							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">怒***4</p>
									<p>特别好，必须赞👍，质量好，很漂亮，快递也好快的。还挺优惠。看图吧。</p>
									<p class="which">颜色:熊猫套装</p>
									<img src="images/shopdetail/detail108.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail109.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail110.jpg" width="40px" height="40px">
								</div>
							</div>
							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">里***2</p>
									<p>不错，很可爱包装很好，赶快下手吧</p>
									<p class="which">颜色:四色小猫</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">啥***2</p>
									<p>一直想要咖啡杯，这次总算拿到手了。很不错的陶瓷杯。图案也很可爱。实物与图一样。</p>
									<p class="which">颜色:熊猫套装</p>
									<img src="images/shopdetail/detail108.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail110.jpg" width="40px" height="40px">
								</div>
							</div>
							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">毛***字</p>
									<p>很精致，用起来很满意，做工也非常的细致，用着很满意哦，非常值得购买</p>
									<p class="which">颜色:创意胡子</p>
									<img src="images/shopdetail/detail103.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail104.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="judge01">
								<div class="idimg"><img src="images/shopdetail/detail102.png"></div>
								<div class="write">
									<p class="idname">轻***4</p>
									<p>店家服务太贴心了，没有破碎，包装非常严实</p>
									<p class="which">颜色:铁塔套装</p>
									<img src="images/shopdetail/detail106.jpg" width="40px" height="40px">
									<img src="images/shopdetail/detail107.jpg" width="40px" height="40px">
								</div>
							</div>

							<div class="clear"></div>
						</div>

						<div class="panel" id="panel03">
							<p class="sell">本店热卖商品</p>
							<div class="com">
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_11.jpg">
										<figcaption>木质花瓶</figcaption>
									</figure>
									<p>木质简约花瓶 亲近大自然</p>
									<div class="bottom"><samp>商城价:￥34元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_12.png">
										<figcaption>假花篮子</figcaption>
									</figure>
									<p>墙上假花优雅系列蓝色篮子</p>
									<div class="bottom"><samp>商城价:￥543元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_13.png">
										<figcaption>富贵花瓶</figcaption>
									</figure>
									<p>白色带金色边创意富贵花瓶</p>
									<div class="bottom"><samp>商城价:￥888元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_14.jpg">
										<figcaption>手工编织花篮</figcaption>
									</figure>
									<p>白色手工编织花篮 小巧简约/p>
										<div class="bottom"><samp>商城价:￥68元</samp><input type="button"
												style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_15.jpg">
										<figcaption>高脚花瓶</figcaption>
									</figure>
									<p>高脚优雅系列花瓶 </p>
									<div class="bottom"><samp>商城价:￥28元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_06.jpg">
										<figcaption>龙猫灯</figcaption>
									</figure>
									<p>创意暖色龙猫小灯</p>
									<div class="bottom"><samp>商城价:￥48元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_07.jpg">
										<figcaption>墙上挂具</figcaption>
									</figure>
									<p>多色可选墙上挂具</p>
									<div class="bottom"><samp>商城价:￥64元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_08.jpg">
										<figcaption>白色小象</figcaption>
									</figure>
									<p>白色小象优雅系列套装</p>
									<div class="bottom"><samp>商城价:￥143元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_09.jpg">
										<figcaption>石制壁饰</figcaption>
									</figure>
									<p>石制墙上创意装饰用品</p>
									<div class="bottom"><samp>商城价:￥348元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>
								<a href="#" class="ex01">
									<figure>
										<img src="images/index_img/content_10.jpg">
										<figcaption>小假山</figcaption>
									</figure>
									<p>小假山室内装饰清新空气</p>
									<div class="bottom"><samp>商城价:￥448元</samp><input type="button"
											style=" cursor:pointer;" value="购买" /></div>
								</a>

							</div>

							<div class="clear"></div>
						</div>


					</div>

				</div>

			</div>

			<!-----商品详情评价部结束分------->

			<!----bottom_页脚部分----->
			<div class="backf">
				<div id="footer">
					<ul>
						<li class="sy">支付方式</li>
						<li><a href="#">在线支付</a></li>
						<li><a href="#">货到付款</a></li>
						<li><a href="#">发票说明</a></li>
						<li><a href="#">余额宝</a></li>

					</ul>
					<ul>
						<li class="sy">购物指南</li>
						<li><a href="#">免费注册</a></li>
						<li><a href="#">申请会员</a></li>
						<li><a href="#">开通支付宝</a></li>
						<li><a href="#">支付宝充值</a></li>
					</ul>
					<ul>
						<li class="sy">商家服务</li>
						<li><a href="#">联系我们</a></li>
						<li><a href="#">客服服务</a></li>
						<li><a href="#">物流服务</a></li>
						<li><a href="#">缺货赔付</a></li>
					</ul>
					<ul>
						<li class="sy">关于我们</li>
						<li><a href="#">知识产权</a></li>
						<li><a href="#">网站合作</a></li>
						<li><a href="#">规则意见</a></li>
						<li><a href="#">帮助中心</a></li>
					</ul>
					<ul>
						<li class="sy">其他服务</li>
						<li><a href="#">诚聘英才</a></li>
						<li><a href="#">法律声明</a></li>

					</ul>
					<div class="clear"></div>
				</div>
				<div class="foot">
					<p>使用本网站即表示接受 尚美衣店用户协议</p>
					<p>版权所有——————————————————</p>

				</div>
			</div>
		</div>
	</body>
	<script>
		const App = {
			data() {
				const handleNum = () => {
					if(this.num == ""){
						this.num = 1;
					}
				}
				return {
					handleNum,
					logined: false,
					currentUser: {
						id: "-1",
						name: "null",
						phone: "null",
						email: "null"
					},
					sellerId: "",
					kw: "",
					goodsForDetail: [
							<#list goodsForDetails as goods>
						{
							skuId: ${goods.skuId},
							sellerId: "${goods.sellerId}",
							goodsName: "${goods.goodsName}",
							sku: '${goods.sku}',
							originalPrice: "${goods.originalPrice}",
							nowPrice: "${goods.nowPrice}",
							stock: ${goods.stock},
							promotionWay: "${goods.promotionWay}",
							previewImgs: [
									<#list goods.previewImgs as p>
										"${p}",
									</#list>
							],
							detailImgs: [
								<#list goods.detailImgs as d>
								"${d}",
								</#list>
							],
							presale: {
								id: ${goods.presale.id},
								goodsId: "${goods.presale.goodsId}",
								presalePrice: ${goods.presale.presalePrice}
							}
						},
							</#list>
					],
					dataStatus: false,
					skuKeys: [],  // 颜色，尺寸
					currStock: 0,
					selectedSku: {},
					selectedSkuId: -1,
					goodsId: "",
					num: 1  ,// 要购买的数量
					unitPrice: "",
					totalPrice: 0
				}
			},
			methods: {
				toLoginPage() {
					const returnUrl = window.location.href + "";
					window.location.href = "http://www.login.hcymall.com" + "?returnUrl=" + returnUrl; // 跳转到登录页面
				},
				toRegPage() {
					const returnUrl = window.location.href + "";
					window.location.href = "http://www.reg.hcymall.com" + "?returnUrl=" + returnUrl; // 跳转到注册页面
				},
				toSearchPage() {
					if ("" == this.kw) {
						this.kw = this.defaultSs;
					}
					window.location.href = "http://www.search.hcymall.com?kw=" + this.kw; // 搜索
				},
				handleSku(){
					this.goodsForDetail.forEach(item => {
						const obj = JSON.parse(item.sku);
						for (let key in obj) {
							if (this.skuKeys.indexOf(key)  == -1){
								this.skuKeys.push(key);
							}
	
						}
					});
				},
				changeStock(key,value){
					this.num = 1;
					// 根据 sku 显示不同sku的库存
					this.selectedSku[key] = value;
					let s = JSON.stringify(this.selectedSku);
					//console.log(s);
					for (let i = 0; i < this.goodsForDetail.length; i++) {
						if(this.goodsForDetail[i].sku == s){
							this.currStock = this.goodsForDetail[i].stock;
							this.selectedSkuId = this.goodsForDetail[i].skuId;
							return;
						}
					}
					this.selectedSkuId = -1;
					this.currStock = 0;
				},
				reduceNum(){
					let n = parseInt(this.num);
					if(n <= 1){
						this.num = 1;
						return;
					}else{
						this.num--;
					}
					
				},
				addNum(){
					let n = parseInt(this.num);
					n++;
					if(n>this.currStock){
						alert("购买数量不能超过库存数量！");
						return;
					}
					this.num = n;
					
				},
				preHandle(){
					this.sellerId = this.goodsForDetail[0].sellerId;
					this.currStock = this.goodsForDetail[0].stock;
					this.selectedSku = JSON.parse(this.goodsForDetail[0].sku);  // 默认显示第一个sku
					this.selectedSkuId = this.goodsForDetail[0].skuId;
					if(this.goodsForDetail[0].presale != null){
						this.unitPrice = this.goodsForDetail[0].presale.presalePrice;
					}else{
						this.unitPrice = this.goodsForDetail[0].nowPrice;
					}
					const x= parseFloat(this.unitPrice);
					this.totalPrice = x*this.num;
					// 处理sku
					this.handleSku();
					this.dataStatus = true;
				},
				buy(){
					if(this.logined == false){
						this.toLoginPage();
						return;
					}
					if(this.currStock == 0 || this.selectedSkuId == -1){
						alert("亲~，没有库存了，请选择其它款式！");
						return;
					}
					console.log("skuid:"+this.selectedSkuId);
					
					// 要传递 skuId、当前用户currentUser的 id、购买数量 num
					window.location.href = "http://www.order_confirm.hcymall.com?skuId=" + this.selectedSkuId + 
						"&currentUserId=" + this.currentUser.id + "&num=" + this.num + "&sellerId=" + this.sellerId;
				},
				refreshStock(){
					for(let i=0;i<this.goodsForDetail.length;i++){
						axios.get("http://www.item.hcymall.com/stock?skuId="+this.goodsForDetail[i].skuId).then(res => {
							if(res.data.code == "1001"){
								const nowStock = parseInt(res.data.stock);
								if(i==0){
									this.currStock = nowStock;
								}
								this.goodsForDetail[i].stock = nowStock;
							}else{
								console.log(res.data.msg);
							}
						});
						for(var t = Date.now();Date.now() - t <= 10;);  // 防止后面的响应覆盖前面的响应结果，所以暂停10毫秒
					}
					this.preHandle();
				}
			},
			computed: {
				skuObj(){
					let skuObj = {};
					this.skuKeys.forEach(item => {
						skuObj[item] = [];
					})
					this.goodsForDetail.forEach(item => {
						const obj = JSON.parse(item.sku);  // 获取sku并转为对象
						this.skuKeys.forEach(item => {
							if (skuObj[item].indexOf(obj[item]) == -1){  
								skuObj[item].push(obj[item]);  // 注意，去重后在添加
							}
						})
					});
					return skuObj;  // {"颜色":["中国红","深邃蓝"],"尺寸":["45英寸","46英寸"]}
				}
			},
			created() {
				const uu = getCookie("currentUser") + "";
				if (uu == "false") {
					this.logined = false; // 没登录
				} else {
					this.currentUser = JSON.parse(uu);
					this.logined = true;
				}
				this.refreshStock();
			},
			mounted() {
				// 必须要有这个方法，即使没有要执行的代码，也必须加，否则样式有概率会出错
				for(var t = Date.now();Date.now() - t <= 1000;);
			}
		}
		Vue.createApp(App).mount('#app');	
	</script>
	<script src="./js/detail_item.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
				var showproduct = {
					"boxid": "showbox",
					"sumid": "showsum",
					"boxw": 400,
					"boxh": 550,
					"sumw": 60, //列表每个宽度,该版本中请把宽高填写成一样
					"sumh": 60, //列表每个高度,该版本中请把宽高填写成一样
					"sumi": 7, //列表间隔
					"sums": 5, //列表显示个数
					"sumsel": "sel",
					"sumborder": 1, //列表边框，没有边框填写0，边框在css中修改
					"lastid": "showlast",
					"nextid": "shownext"
				}; //参数定义	  
				$.ljsGlasses.pcGlasses(showproduct); //方法调用，务必在加载完后执行
					
				$(function() {
					
					$('.tabs a').click(function() {
					
						var $this = $(this);
						$('.panel').hide();
						$('.tabs a.active').removeClass('active');
						$this.addClass('active').blur();
						var panel = $this.attr("href");
						$(panel).show();
						return false; //告诉浏览器  不要纸箱这个链接
					}) //end click
					
					
					$(".tabs li:first a").click(); //web 浏览器，单击第一个标签吧
					
					$(".centerbox ul").each(function() {
							var i = $(this);
							var p = i.find("li");
							
							p.click(function() {
								if (!!$(this).hasClass("now")) {
									//$(this).removeClass("now");
					
								} else {
									$(this).addClass("now").siblings("li").removeClass("now");
					
								}
					
							})
					
						});
					});
			
			
			}) //end ready
			
	</script>
</html>
