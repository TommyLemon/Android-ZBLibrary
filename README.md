# ZBLibrary-Android快速开发框架
坐标标准库ZBLibrary，是一个MVP架构的Android快速开发框架，提供一套开发标准（UI,Data,Listener）以及模板和工具类并规范代码。
封装层级少，简单高效兼容性好。Android Studio、Eclipse多平台支持。

OKHttp、UIL图片加载、ZXing二维码、下载安装、自动缓存以及各种Base、Demo、UI、Util直接用。

全新的手势，侧滑返回、全局右滑返回都OUT啦！

BaseHttpListActivity，几行代码搞定http请求列表 加载和缓存;

BaseView，自定义View竟然如此简单;

万能的Entry<K, V>，两个变量的Model/JavaBean再也不用写了;

100多个常用style，一行搞定View属性，一键统一配置UI... 

点击右边链接查看如何使用[http://my.oschina.net/u/2437072/blog/665241](http://my.oschina.net/u/2437072/blog/665241)


![](http://images.cnblogs.com/cnblogs_com/tommylemon/848395/o_ALL.jpg)

## 用到的开源库
[OKHttp](https://github.com/square/okhttp)
很火很强大的Http/Https传输框架。ZBLibrary中的HttpManager对它做了封装（支持自签名Https），Demo中的HttpRequest是使用示例。
注：okio.jar是OKHttp的一部分，不能删除。删除后不会在代码中报错，但运行会出错。

[Android-Universal-Image-Loader](https://github.com/nostra13/Android-Universal-Image-Loader)
非常强大的图片加载库，我在ZBLibrary中写了一个ImageLoaderUtil来简化使用它。
UIL唯一的缺点是不支持动态加载，需要动态加载建议用Google的Glide。

[ZXingLib](https://github.com/xuyisheng/ZXingLib)
这是该作者对官方ZXing二维码库的精简版。我做了修改，作为ZBLibrary的QRCodeLibrary。

[fastjson](https://github.com/alibaba/fastjson)
阿里巴巴对原生JSON的封装，简化了JSON的使用。ZBLibrary中的Json是对FastJson的简单封装，防止解析异常。

[pinyin4j](https://sourceforge.net/projects/pinyin4j)
中文转拼音的框架，貌似ZBLibrary没用上，考虑去掉。

## 欢迎Star，欢迎Fork

[https://github.com/TommyLemon/Android-ZBLibrary](https://github.com/TommyLemon/Android-ZBLibrary)

## 下载试用

[ZBLibraryDemoApp.apk](http://files.cnblogs.com/files/tommylemon/ZBLibraryDemoApp.apk)




##相关推荐
[Android快速开发框架-ZBLibrary介绍](http://my.oschina.net/u/2437072/blog/662017)

[如何使用ZBLibrary-Android快速开发框架](http://my.oschina.net/u/2437072/blog/665241)

[全新的手势，侧滑返回、全局右滑返回都OUT啦！](http://www.cnblogs.com/tommylemon/p/5576337.html)

[自定义ZXing二维码扫描界面并解决取景框拉伸等问题](http://my.oschina.net/u/2437072/blog/687986)

[零门槛！ZBLibrary仿微信朋友圈自定义View，就是这么简单！](http://my.oschina.net/u/2437072/blog/666625)

[ListView滑动不爽,滚动一页得滑几次？该用分页列表啦！](http://my.oschina.net/u/2437072/blog/700674)

[高灵活低耦合Adapter快速开发攻略](http://my.oschina.net/u/2437072/blog/701165)

[万能的Entry，两个变量的Model/JavaBean再也不用写了！](http://my.oschina.net/u/2437072/blog/671895)

[Android HTTPS如何10分钟实现自签名SSL证书](http://my.oschina.net/u/2437072/blog/669041)
