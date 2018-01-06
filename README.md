# ZBLibrary-Android快速开发框架 

[![GitHub release](https://img.shields.io/github/release/TommyLemon/Android-ZBLibrary.svg)](https://github.com/TommyLemon/Android-ZBLibrary/releases)
[![](https://jitpack.io/v/TommyLemon/Android-ZBLibrary.svg)](https://jitpack.io/#TommyLemon/Android-ZBLibrary)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels)
[![Gradle Version](https://img.shields.io/badge/gradle-2.10%2B-green.svg)](https://docs.gradle.org/current/release-notes)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


坐标标准库ZBLibrary，是一个MVP架构的Android快速开发框架，提供一套开发标准（View,Data,Event）以及模板和工具类并规范代码。
封装层级少，简单高效兼容性好。Android Studio、Eclipse多平台支持。

OKHttp、UIL图片加载、ZXing二维码、沉浸状态栏、下载安装、自动缓存以及各种Base、Demo、UI、Util直接用。

全新的手势，侧滑返回、全局右滑返回都OUT啦！

用[BaseView](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary(AndroidStudio)/ZBLibrary/src/main/java/zuo/biao/library/base/BaseView.java)，自定义View竟然如此简单;

用[Entry<K, V>](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary(AndroidStudio)/ZBLibrary/src/main/java/zuo/biao/library/model/Entry.java)，两个变量的Model/JavaBean再也不用写了;

用[BaseHttpListActivity](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary(AndroidStudio)/ZBLibrary/src/main/java/zuo/biao/library/base/BaseHttpListActivity.java)，几行代码搞定http请求列表 加载和缓存;

还有100多个常用[style](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary(AndroidStudio)/ZBLibrary/src/main/res/values/styles.xml)，一行搞定View属性，一键统一配置UI... 

点击右边链接查看如何使用[http://my.oschina.net/u/2437072/blog/665241](http://my.oschina.net/u/2437072/blog/665241)


![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/AllPages.jpg?raw=true)

[以下Gif图看起来比较卡，在手机上App运行很流畅] 

![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/screenshot/Cache.gif)
![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/screenshot/PlacePicker.gif)
![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/screenshot/DatePicker.gif)



## 用到的开源库
[SystemBarTint](https://github.com/jgilfelt/SystemBarTint)
系统栏管理器，我把它用在ZBLibrary的BaseActivity中实现了状态栏沉浸。

[Glide](https://github.com/bumptech/glide)
Google开发的一个强大易用的图片加载库。

[OKHttp](https://github.com/square/okhttp)
很火很强大的Http/Https传输框架。ZBLibrary中的HttpManager对它做了封装（支持自签名Https），Demo中的HttpRequest是使用示例。
注：okio.jar是OKHttp的一部分，不能删除。删除后不会在代码中报错，但运行会出错。

[FastJson](https://github.com/alibaba/fastjson)
阿里巴巴的JSON封装和解析库。ZBLibrary中的JSON是对FastJson的简单封装，防止解析异常。

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
下拉刷新、上拉加载的库，兼容任意View，酷炫又好用。已在[BaseHttpRecyclerActivity](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/ZBLibrary(AndroidStudio)/ZBLibrary/src/main/java/zuo/biao/library/base/BaseHttpRecyclerActivity.java)等类内置支持。

[ZXingLib](https://github.com/xuyisheng/ZXingLib)
这是该作者对官方ZXing二维码库的精简版。我做了修改，作为ZBLibrary的QRCodeLibrary。


## 初始化

假设你工程中的Application为zblibrary.demo.application.DemoApplication，并且已在AndroidManifest.xml中注册
```
    <application
        android:name="zblibrary.demo.application.DemoApplication"
        ...
        >
    </application>
```

可以用DemoApplication继承BaseApplication
```
public class DemoApplication extends BaseApplication {
...
}
```
或 在DemoApplication的 onCreate函数 中调用 BaseApplication.init(this);
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

注册 权限和Activity 等代码可复制ZBLibrary中AndroidManifest.xml里的 \<uses-permission/>, \<activity/> 等相关代码。

## 编程思想
* 能复制就复制，节约时间避免出错
* 保留原本结构，简单上手容易调试
* 增加必要注释，说明功能和使用方法
* 说明随手可得，不用上网或打开文档
* 命名必须规范，容易查找一看就懂
* 重载尽量转发，减少代码容易修改
* 最先参数判错，任意调用不会崩溃
* 代码模块分区，方便浏览容易查找
* 封装常用代码，方便使用降低耦合
* 回收多余占用，优化内存提高性能
* 分包结构合理，模块清晰浏览方便
* 多用工具和快捷键，增删改查快捷高效

## 相关推荐
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

## 开发规范
[Wiki-Norm](https://github.com/TommyLemon/Android-ZBLibrary/wiki/%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83)

## 架构、模式、技术
[Wiki-Architecture,Pattern,Technology](https://github.com/TommyLemon/Android-ZBLibrary/wiki/%E6%9E%B6%E6%9E%84%E3%80%81%E6%A8%A1%E5%BC%8F%E3%80%81%E6%8A%80%E6%9C%AF)

## 问题及解决方法
[Wiki-FAQ](https://github.com/TommyLemon/Android-ZBLibrary/wiki/%E9%97%AE%E9%A2%98%E5%8F%8A%E8%A7%A3%E5%86%B3%E6%96%B9%E6%B3%95)

## 关于作者
TommyLemon：[https://github.com/TommyLemon](https://github.com/TommyLemon) <br >
QQ技术交流群：595514898

如果有什么问题或建议可以[提ISSUE](https://github.com/TommyLemon/Android-ZBLibrary/issues)、加群或者[发我邮件](https://github.com/TommyLemon)，交流技术，分享经验。<br >
如果你解决了某些bug，或者新增了一些通用性强的功能，欢迎[贡献代码](https://github.com/TommyLemon/Android-ZBLibrary/pulls)，感激不尽^_^

## 下载试用

[ZBLibraryDemoApp.apk(ZBLibrary)](http://files.cnblogs.com/files/tommylemon/ZBLibraryDemoApp.apk)

[APIJSONClientApp.apk(APIJSON+ZBLibrary)](http://files.cnblogs.com/files/tommylemon/APIJSONApp.apk)

## 持续更新
[https://github.com/TommyLemon/Android-ZBLibrary/commits/master](https://github.com/TommyLemon/Android-ZBLibrary/commits/master)

## 我要赞赏
如果你喜欢ZBLibrary，感觉ZBLibrary帮到了你，可以点右上角 ⭐Star 支持一下，谢谢 ^_^
[https://github.com/TommyLemon/Android-ZBLibrary](https://github.com/TommyLemon/Android-ZBLibrary)
