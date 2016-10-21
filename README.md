# ZBLibrary-Android快速开发框架
坐标标准库ZBLibrary，是一个MVP架构的Android快速开发框架，提供一套开发标准（UI,Data,Event）以及模板和工具类并规范代码。
封装层级少，简单高效兼容性好。Android Studio、Eclipse多平台支持。

OKHttp、UIL图片加载、ZXing二维码、沉浸状态栏、下载安装、自动缓存以及各种Base、Demo、UI、Util直接用。

全新的手势，侧滑返回、全局右滑返回都OUT啦！

BaseHttpListActivity，几行代码搞定http请求列表 加载和缓存;

BaseView，自定义View竟然如此简单;

万能的Entry<K, V>，两个变量的Model/JavaBean再也不用写了;

100多个常用style，一行搞定View属性，一键统一配置UI... 

点击右边链接查看如何使用[http://my.oschina.net/u/2437072/blog/665241](http://my.oschina.net/u/2437072/blog/665241)


![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/AllPages.jpg?raw=true)

[以下Gif图看起来比较卡，实际上手机运行很流畅] 

![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/screenshot/Cache.gif)
![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/screenshot/DatePickerWindow.gif)
![](https://github.com/TommyLemon/Android-ZBLibrary/blob/master/screenshot/PageScroller.gif)


## 用到的开源库
[SystemBarTint](https://github.com/jgilfelt/SystemBarTint)
系统栏管理器，我把它用在ZBLibrary的BaseActivity中实现了状态栏沉浸。

[Android-Universal-Image-Loader](https://github.com/nostra13/Android-Universal-Image-Loader)
非常强大的图片加载库，我在ZBLibrary中写了一个ImageLoaderUtil来简化使用它。
UIL唯一的缺点是不支持动态加载，需要动态加载建议用Google的Glide。

[XListView-Android](https://github.com/Maxwin-z/XListView-Android)
下拉刷新、上拉加载的ListView。我修改了部分代码使其支持打开即刷新（带动画）以及自动加载更多（无需上拉）。

[OKHttp](https://github.com/square/okhttp)
很火很强大的Http/Https传输框架。ZBLibrary中的HttpManager对它做了封装（支持自签名Https），Demo中的HttpRequest是使用示例。
注：okio.jar是OKHttp的一部分，不能删除。删除后不会在代码中报错，但运行会出错。

[FastJson](https://github.com/alibaba/fastjson)
阿里巴巴对原生JSON的封装，简化了JSON的使用。ZBLibrary中的Json是对FastJson的简单封装，防止解析异常。

[ZXingLib](https://github.com/xuyisheng/ZXingLib)
这是该作者对官方ZXing二维码库的精简版。我做了修改，作为ZBLibrary的QRCodeLibrary。

[PagedListView](https://github.com/TommyLemon/PagedListView)
Scroll ListView faster, more accurate and comfortable.

##编程思想
* 能复制就复制，节约时间避免出错
* 保留原本结构，简单上手容易调试
* 说明随手可得，不用上网或打开文档
* 增加必要注释，说明功能和使用方法
* 命名尽量规范，容易查找一看就懂
* 函数尽量嵌套，减少代码容易修改
* 最先参数判错，保证外部任意调用
* 代码模块分区，浏览方便容易查找
* 封装常用代码，方便使用降低耦合
* 回收多余占用，优化内存提高性能
* 分包结构合理，模块清晰浏览方便

##相关推荐
[Android快速开发框架-ZBLibrary介绍](http://my.oschina.net/u/2437072/blog/662017)

[如何使用ZBLibrary-Android快速开发框架](http://my.oschina.net/u/2437072/blog/665241)

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


##关于作者
Email：<tommylemon@qq.com>      QQ群：595514898

有任何建议或者使用中遇到问题都可以给我发邮件，也可以加群，交流技术，分享经验 ^_^

## 欢迎Star，欢迎Fork

[https://github.com/TommyLemon/Android-ZBLibrary](https://github.com/TommyLemon/Android-ZBLibrary)

## 下载试用

[ZBLibraryDemoApp.apk](http://files.cnblogs.com/files/tommylemon/ZBLibraryDemoApp.apk)
