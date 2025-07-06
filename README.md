# ZBLibrary-Android快速开发框架 
[<img src="https://devin.ai/assets/deepwiki-badge.png" alt="Ask DeepWiki.com" height="12px" style="height: 20px" />](https://deepwiki.com/TommyLemon/Android-ZBLibrary)
[![](https://jitpack.io/v/TommyLemon/Android-ZBLibrary.svg)](https://jitpack.io/#TommyLemon/Android-ZBLibrary)
[![Android](https://img.shields.io/badge/Android-4.0.3%2B-brightgreen.svg?style=flat)](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels)
[![Gradle Version](https://img.shields.io/badge/gradle-2.10%2B-green.svg)](https://docs.gradle.org/current/release-notes)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) 
[<img src="https://whitehub.net/cystack/img/logo.c0ea980.svg" alt="WhiteHub.net" height="12" style="height: 20px" />](https://whitehub.net/programs/zblibrary)

MVP 架构，提供一套开发标准（View, Data, Event）以及模板和工具类并规范代码。封装层级少，简单高效兼容性好。

OKHttp 网络请求、Glide 图片加载、ZXing 二维码、沉浸状态栏、下载安装、自动缓存以及各种 Base、Demo、UI、Util 直接用。

全新的手势，侧滑返回、全局右滑返回都 OUT 啦！

用 [BaseView](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary/src/main/java/zuo/biao/library/base/BaseView.java)，自定义 View 竟然如此简单;

用 [Entry<K, V>](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary/src/main/java/zuo/biao/library/model/Entry.java)，两个变量的 Model/JavaBean 再也不用写了;

用 [BaseHttpListActivity](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary/src/main/java/zuo/biao/library/base/BaseHttpListActivity.java)，几行代码搞定 HTTP 请求列表 加载和缓存;

还有 100 多个常用 [Style](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary/src/main/res/values/styles.xml)，一行搞定 View 属性，一键统一配置UI... 

点击右边链接查看如何使用 [http://my.oschina.net/u/2437072/blog/665241](http://my.oschina.net/u/2437072/blog/665241)


![](https://raw.githubusercontent.com/TommyLemon/StaticResources/master/ZBLibrary_AllPages.jpg)

[以下 Gif 图看起来比较卡，实际在手机上 App 运行很流畅]

ZBLibraryDemoApp<br />

![](https://raw.githubusercontent.com/TommyLemon/StaticResources/master/ZBLibrary_Cache.gif)
![](https://raw.githubusercontent.com/TommyLemon/StaticResources/master/ZBLibrary_PlacePicker.gif)
![](https://raw.githubusercontent.com/TommyLemon/StaticResources/master/ZBLibrary_DatePicker.gif)

<br />
APIJSONApp<br />

![](https://raw.githubusercontent.com/TommyLemon/StaticResources/master/APIJSON_App_MomentList_Circle.gif) 
![](https://raw.githubusercontent.com/TommyLemon/StaticResources/master/APIJSON_App_Moment_Name.gif) 
![](https://raw.githubusercontent.com/TommyLemon/StaticResources/master/APIJSON_App_Moment_Comment.gif)


### 下载试用

[ZBLibraryDemoApp.apk(ZBLibrary)](http://files.cnblogs.com/files/tommylemon/ZBLibraryDemoApp.apk)

[APIJSONApp.apk(APIJSON+ZBLibrary)](http://files.cnblogs.com/files/tommylemon/APIJSONApp.apk)

### 感谢开源
[Glide](https://github.com/bumptech/glide)
Google 官方推荐的一个强大易用的图片加载库。

[OKHttp](https://github.com/square/okhttp)
很火很强大的 HTTP/HTTPS 传输框架。ZBLibrary 中的 HttpManager 对它做了封装（支持自签名Https），Demo 中的 HttpRequest 是使用示例。
注：okio.jar 是 OKHttp 的一部分，不能删除。删除后不会在代码中报错，但运行会出错。

[FastJson](https://github.com/alibaba/fastjson)
阿里巴巴的 JSON 封装和解析库。ZBLibrary 中的 JSON 是对 fastjson 的简单封装，防止解析异常。

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
下拉刷新、上拉加载的组件库，已在 [BaseHttpRecyclerActivity](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary/src/main/java/zuo/biao/library/base/BaseHttpRecyclerActivity.java) 等类内置支持。

[SystemBarTint](https://github.com/jgilfelt/SystemBarTint)
系统栏管理器，我把它用在 ZBLibrary 的 BaseActivity 中实现了状态栏沉浸。

[ZXingLib](https://github.com/xuyisheng/ZXingLib)
这是该作者对官方 ZXing 二维码库的精简版。我做了修改，作为 ZBLibrary 的 QRCodeLibrary。


### 初始化

假设你工程中的 Application 为 zblibrary.demo.application.DemoApplication，并且已在 AndroidManifest.xml 中注册
```
    <application
        android:name="zblibrary.demo.application.DemoApplication"
        ...
        >
    </application>
```

可以用 DemoApplication 继承 BaseApplication
```
public class DemoApplication extends BaseApplication {
...
}
```
或 在 DemoApplication 的 onCreate 函数 中调用 BaseApplication.init(this);
```
public class DemoApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		BaseApplication.init(this);
	}
  ...
}
```

注册 权限和 Activity 等代码可复制 ZBLibrary 中 AndroidManifest.xml 里的 \<uses-permission/>, \<activity/> 等相关代码。

### 生成代码
可使用 APIAuto 自动化接口管理工具来生成接口相关代码：
* 自动生成封装请求 JSON 的代码
* 自动生成解析结果 JSON 的代码
* 自动生成 Modle/JavaBean

[https://github.com/TommyLemon/APIAuto](https://github.com/TommyLemon/APIAuto)

### 编程思想
* 能复制就复制，节约时间避免出错
* 保留原本结构，简单上手容易调试
* 增加必要注释，说明功能和使用方法
* 说明随手可得，不用上网或打开文档
* 命名必须规范，容易查找一看就懂
* 重载尽量转发，减少代码容易修改
* 最先校验参数，任意调用不会崩溃
* 代码模块分区，方便浏览容易查找
* 封装常用代码，方便使用降低耦合
* 回收多余占用，优化内存提高性能
* 分包结构合理，模块清晰浏览方便
* 多用工具和快捷键，增删改查快捷高效

### 相关推荐
[Android快速开发框架-ZBLibrary介绍](http://my.oschina.net/u/2437072/blog/662017)

[如何使用ZBLibrary-Android快速开发框架](http://my.oschina.net/u/2437072/blog/665241)

[仿QQ空间和微信朋友圈，高解耦高复用高灵活](https://my.oschina.net/tommylemon/blog/885787)

[全新的手势，侧滑返回、全局右滑返回都OUT啦！](http://www.cnblogs.com/tommylemon/p/5576337.html)

[不一样的Android选择器，简单方便，地址日期时间都好用！](https://my.oschina.net/u/2437072/blog/756271)

[自定义ZXing二维码扫描界面并解决取景框拉伸等问题](http://my.oschina.net/u/2437072/blog/687986)

[零门槛！ZBLibrary仿微信朋友圈自定义View，就是这么简单！](http://my.oschina.net/u/2437072/blog/666625)

[BaseHttpListActivity，几行代码搞定Http列表请求、加载和缓存](http://my.oschina.net/u/2437072/blog/726229)

[ListView滑动不爽,滚动一页得滑几次？该用分页列表啦！](http://my.oschina.net/u/2437072/blog/700674)

[高灵活低耦合Adapter快速开发攻略](http://my.oschina.net/u/2437072/blog/701165)

[万能的Entry，两个变量的Model/JavaBean再也不用写了！](http://my.oschina.net/u/2437072/blog/671895)

[Android HTTPS如何10分钟实现自签名SSL证书](http://my.oschina.net/u/2437072/blog/669041)

[100多个Styles快速开发布局XML，一行搞定View属性，一键统一配置UI...](http://my.oschina.net/u/2437072/blog/716573)


### 开发规范
[Wiki-Norm](https://github.com/TommyLemon/Android-ZBLibrary/wiki/%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83)

### 架构、模式、技术
[Wiki-Architecture,Pattern,Technology](https://github.com/TommyLemon/Android-ZBLibrary/wiki/%E6%9E%B6%E6%9E%84%E3%80%81%E6%A8%A1%E5%BC%8F%E3%80%81%E6%8A%80%E6%9C%AF)

### 问题及解决方法
[Wiki-FAQ](https://github.com/TommyLemon/Android-ZBLibrary/wiki/%E9%97%AE%E9%A2%98%E5%8F%8A%E8%A7%A3%E5%86%B3%E6%96%B9%E6%B3%95)

### 使用登记
<div style="float:left">
  <a href="https://www.leapmotor.com/"><img src="https://pp.myapp.com/ma_icon/0/icon_52688286_1551098818/96" height="60"></a>
  <a href="http://www.egatee.com"><img src="https://github.com/TommyLemon/StaticResources/blob/master/Egatee.webp" height="60"></a>
  <a href="http://www.hzviewin.com/cxb"><img src="https://android-artworks.25pp.com/fs08/2018/11/07/5/106_d33fcebea05e7bc1510a606d4bff86c7_con_130x130.png" height="60"></a>
  <a href="http://www.dzwww.com/shandong/sdnews/201803/t20180305_17109009.htm"><img src="https://android-artworks.25pp.com/fs08/2018/03/12/1/110_d483f8186cd7ffb2f1bf1992f250e722_con_130x130.png" height="60"></a>
  <a href="https://sj.qq.com/myapp/detail.htm?apkName=com.pianfu.hungrybat"><img src="https://pp.myapp.com/ma_icon/0/icon_42293553_1479800504/96" height="60"></a>
  <a href="https://www.wandoujia.com/apps/com.pobing.crafts"><img src="https://android-artworks.25pp.com/fs08/2016/06/07/10/1_dc9bffc8d352e7eb329a0851cf5bb097_con_130x130.png" height="60"></a>
  <a href="http://zhushou.360.cn/detail/index/soft_id/3276581?recrefer=SE_D_%E5%BF%AB%E7%94%A8%E9%80%9A%E8%AE%AF%E5%BD%95"><img src="http://p17.qhimg.com/t01ed3c14480b8bef6b.png" height="60"></a>
  <a href="http://zhushou.360.cn/detail/index/soft_id/2687307?recrefer=SE_D_hi%E5%88%9D%E8%A7%81"><img src="http://p16.qhimg.com/t01dccaedd0ebed2ad9.png" height="60"></a>
  <a href="http://www.bjzxyy.com"><img src="https://raw.githubusercontent.com/TommyLemon/StaticResources/master/BaoJiShiZhongXinYiYuan.png" height="60"></a>
  <a href="https://github.com/TommyLemon/APIJSON"><img src="https://raw.githubusercontent.com/TommyLemon/StaticResources/master/APIJSON_Logo.png" height="60"></a>
</div>
<br />

* 深圳市传音通讯有限公司
* 浙江零跑科技股份有限公司
* 杭州妙影微电子有限公司
* 山东瑞翔科技有限公司
* 杭州市珀冰科技有限公司
* 魔笛手(杭州)网络科技有限公司
* 佛山市手艺人有限公司
* 山东国大智能科技有限公司
* 宝鸡中心医院

[您在使用 ZBLibrary 吗？](https://github.com/TommyLemon/Android-ZBLibrary/issues/18)


### 关于作者
[https://github.com/TommyLemon](https://github.com/TommyLemon)<br />
<img width="1280" src="https://github.com/TommyLemon/UIGO/assets/5738175/ec77df98-ff9b-43aa-b2f1-2fce2549d276">

<div style="float:left">
  <a href="https://github.com/TommyLemon"><img src="https://avatars1.githubusercontent.com/u/5738175?s=400&u=5b2f372f0c03fae8f249d2d754e38971c2e17b92&v=4" height="90" width="90" ></a>
  <a href="https://github.com/TommyLemon/Android-ZBLibrary/pull/37"><img src="https://avatars1.githubusercontent.com/u/34591987?s=400&v=4" height="90" width="90" ></a>
  <a href="https://github.com/TommyLemon/Android-ZBLibrary/pull/26"><img src="https://avatars1.githubusercontent.com/u/8438560?s=460&v=4"  height="90" width="90" ></a>
  <a href="https://github.com/TommyLemon/Android-ZBLibrary/pull/31"><img src="https://avatars1.githubusercontent.com/u/45145447?s=400&v=4"  height="90" width="90" ></a>
  <a href="https://github.com/TommyLemon/Android-ZBLibrary/pull/2"><img src="https://avatars0.githubusercontent.com/u/9335665?s=460&v=4"  height="90" width="90" ></a>
  <a href="https://github.com/TommyLemon/Android-ZBLibrary/pull/33"><img src="https://avatars2.githubusercontent.com/u/30994405?s=400&v=4"  height="90" width="90" ></a>
  <a href="https://github.com/TommyLemon/Android-ZBLibrary/pull/39"><img src="https://avatars0.githubusercontent.com/u/1075578?s=400&v=4"  height="90" width="90" ></a>
</div>
<br />
感谢其它作者的贡献。

#### QQ技术交流群
1050166440（新）<a target="_blank" style="bottom:2px;padding-top:4px" href="https://qm.qq.com/cgi-bin/qm/qr?k=GTjiuzvEO0yaajNv-vy3bNnGDJPygv0i&jump_from=webapi"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="ZBLibrary3" title="ZBLibrary技术交流群3"  style="bottom:2px;margin-top:4px" /></a>    
421793905（满） <a target="_blank" style="bottom:2px;padding-top:4px" href="https://qm.qq.com/cgi-bin/qm/qr?k=FnufE63OWowsjOtO970SMiHKW873dka9&jump_from=webapi"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="ZBLibrary2" title="ZBLibrary技术交流群2"  style="bottom:2px;margin-top:4px" /></a>    
595514898（满）<a target="_blank" style="bottom:2px;padding-top:4px" href="https://qm.qq.com/cgi-bin/qm/qr?k=gfm9X2yO2cUq0jwND3Hgkh12zfjeGdtC&jump_from=webapi"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="ZBLibrary1" title="ZBLibrary技术交流群1"  style="bottom:2px;margin-top:4px" /></a>    

如果有什么问题或建议可以 [提 ISSUE ](https://github.com/TommyLemon/Android-ZBLibrary/issues) 或 加群，交流技术，分享经验。<br >
如果你解决了某些 Bug，或者新增了一些功能，欢迎 [贡献代码](https://github.com/TommyLemon/Android-ZBLibrary/pulls)，感激不尽^_^


### 其它项目
[APIJSON](https://github.com/Tencent/APIJSON) 🚀 腾讯零代码、全功能、强安全 ORM 库 🏆 后端接口和文档零代码，前端(客户端) 定制返回 JSON 的数据和结构

[APIAuto](https://github.com/TommyLemon/APIAuto) 敏捷开发最强大易用的接口工具，机器学习零代码测试、生成代码与静态检查、生成文档与光标悬浮注释

[UnitAuto](https://github.com/TommyLemon/UnitAuto) 机器学习零代码单元测试平台，零代码、全方位、自动化 测试 方法/函数 的正确性、可用性和性能

[SQLAuto](https://github.com/TommyLemon/SQLAuto) 智能零代码自动化测试 SQL 语句执行结果的数据库工具，一键批量生成参数组合、快速构造大量测试数据

[UIGO](https://github.com/TommyLemon/UIGO) 📱 零代码快准稳 UI 智能录制回放平台 🚀 自动兼容任意宽高比分辨率屏幕、自动精准等待网络请求，录制回放快、准、稳！

[APIJSON-Android-RxJava](https://github.com/TommyLemon/APIJSON-Android-RxJava) ZBLibrary(UI) + APIJSON(HTTP) + RxJava(Data)

[AbsGrade](https://github.com/APIJSON/AbsGrade) 列表级联算法，支持微信朋友圈单层评论、QQ空间双层评论、百度网盘多层(无限层)文件夹等

[PagedListView](https://github.com/TommyLemon/PagedListView) 分页滑动列表工具，优化 ListView/GridView 的滑动和滚动体验

[ohos-ZBLibrary](https://gitee.com/HarmonyOS-tpc/ohos-ZBLibrary) 华为开源鸿蒙 OS 版 ZBLibrary

### 持续更新
[https://github.com/TommyLemon/Android-ZBLibrary/commits/master](https://github.com/TommyLemon/Android-ZBLibrary/commits/master)

### 我要赞赏
腾讯、中国移动、中国电信、华为、字节跳动、阿里巴巴、美团、网易、百度、京东、小米、TCL、万达、中兴、携程、360、美图、同花顺、用友、金蝶 等 和国外 Google, Microsoft, Amazon, SAMSUNG, Airbnb, ThoughtWorks, DHC 等各大知名大厂员工点了 Star，感谢大家的支持~ <br />
![image](https://github.com/TommyLemon/Android-ZBLibrary/assets/5738175/cc4d0930-92c9-4b8b-8a30-99fc776923a6)

**创作不易、坚持更难，右上角点亮 ⭐Star 支持/收藏下本项目吧，谢谢 ^_^** <br />
[https://github.com/TommyLemon/Android-ZBLibrary](https://github.com/TommyLemon/Android-ZBLibrary)
