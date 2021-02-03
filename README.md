# What  

该框架基于 Jetpack + Arouter + Retrofit + MVVM等实现的一款开源学习项目，该框架存在的意义一方面是为了能实现快速开发、协同开发、实现可复用等，另一方面是想把自己会的、有趣的、可复用的东西写出来，分Java、Kotlin版本

## [流程图 - 项目业务开发方向](https://www.processon.com/view/link/5ee9e48407912929cb49f28d)

### [组件化流程图 - 大概](https://www.processon.com/view/link/5ee9d5bdf346fb1ae569847d)

#### [下载地址，Java版](https://www.pgyer.com/What)，或扫码下载 【不一定记得更新，还是得自己拉代码跑】
![image](https://www.pgyer.com/app/qrcode/What)

#### 项目
-  主要使用了 JitPack 、 ARouter 、RxJava2 、Retrofit2 等主流框架，MVVM模式
-  主工程运行、业务模块独立运行
-  各模块相互跳转、通信等
-  功能、业务分离解耦


#### 框架&工具

##### 工欲善其事，必先利其器 skr

-   `utils` 包下 **存放无业务逻辑相关的常用工具 轻量级的**
    -   Android & Java 的工具库，通过静态方法封装，降低相关API的学习成本，提高工作效率
    -   Okhttp3 `util_okhttp`：集成 `Okhttp3`，简单的抽象封装，实现网络请求
	-	...

-  `widget`包下 **存放与业务逻辑相关的组件工具**
    -	通用框架 `lib_common`：简单的集成了通用的 toast、log、状态栏等工具类，部分lib包下都会集成该框架
    -	底层框架 `lib_arch` ：集成 `JetPack` ，解决单继承问题，只需实现 `IActivity` 、 `IFragment` 和 部分注解 `@BindRes`、`@BindViewModel`等
    -	适配器 `lib_adapter`：封装 普通 and `Paging` 两种适配器，实现头尾、增删查改、多状态布局等
    -	数据库 room `lib_room`：集成 `Room` 简单封装了个泛型BaseDao，少实现增删改
    -   网络请求 `lib_network`：
        -	java： `Retrofit2` + `RxJava2` + `Okhttp3`
        -	kotlin：`Retrofit2` + `Okhttp3` + `Coroutine`
    -	...

-  自行查看 `utils` `widget`目录，工具包都在其包下。具体实现方法可在 `module_widget` 模块下查看 (不完全)


开发环境：Android Studio 4.1.1 、gradle 6.6 、kotlin 1.4.10 、JDK 1.8 、SdkVersion support 28 - AndroidX 30

- 最新Java + support版本的看 [java-support](https://github.com/y1xian/What/tree/java-support) 分支；

- 最新Java + AndroidX版本的看 [java-androidx](https://github.com/y1xian/What/tree/java-androidx) 分支；

- 最新AndroidX + Kotlin版本的看 [kt-androidx](https://github.com/y1xian/What/tree/kt-androidx) 分支，**【 kotlin版本会比java版本更新慢，重心还在java版本上（kotlin也就图一乐，吃饭还是得靠java 】**；

- 主干与其它分支：主干会同步 `java-support` 分支代码，也可能会同步不及时，所以还是看分支优先；其它久远分支可能废弃掉了或不更新了。


#### 模块功能
（PS：各模块还处于打磨中，一堆bug，辣眼勿怪. **功能点只列举个别，包含但不限于**）
- `app`主模块
    - 只用于打包、配置 `gradle` 个别信息

- `module_widget`模块（该模块只引用了`common_base`,区别于其他模块）
    - 存放各种自定义的 `lib_` 包 （于`widget`包下），方便发布 `JitPack` 导入使用
    - [x] 调试功能组件

- `module_main`模块
    - 启动页、闪屏页、主界面布局

- `module_login`模块
    - 登录、注册界面及功能
    - [x] 提供、存储用户信息

- `module_user`模块
    - 用户个人界面
    - [x] 展示信息
    - [ ] 编辑信息

- `module_wanandroid`模块
    - 使用 [wanandroid](https://www.wanandroid.com/) 提供的API，开发的一款简略版客户端
    - [x] 部分功能点
    - [x] 跳转web
    - [x] 内容搜索

- `module_video`模块
    - 模仿抖音列表及部分基础功能
    - [x] 播放列表
    - [ ] 详情页
    - [ ] 评论功能

- `module_joke`模块
    - 模块皮皮虾的视频、图文界面及部分基础功能
    - [x] 多状态列表
    - [ ] 详情页
    - [ ] 评论功能

- `module_music`模块
    - 音乐播放基础功能
    - [x] 本地音乐列表、
    - [ ] 网络音乐列表，**无合适API
    - [x] 通知栏控制

- `module_novel`模块
    - 小说基础功能
    - [x] 列表页
    - [x] 小说阅读页、翻页效果、缓存
    - [x] 书架页

- `module_caht`模块
    - 接入`环信SDk` 在其基础上开发
    - [ ] 聊天室
    - [ ] 群聊

- `module_news`模块
    - 新闻资讯基础功能
    - [ ] 列表
    - [ ] 详情

- `module_mall`模块
    - 商城基础功能
    - [ ] 商品列表
    - [ ] 商品详情
    - [ ] 购物车
    - [ ] 支付流程
    - [ ] 订单状态

- `module_live`模块
    - 直播基础功能
    - [ ] 直播列表
    - [ ] 直播室，推拉流
    - [ ] 聊天室
    - [ ] 礼物效果

---

### 组件化单项目运行
1. 在 `local.properties` 下添加，`模块名=true`
```
#module_login=true
module_wanandroid=true
module_user=true
...
```
之后build一下

2. 如在单项目需要引用到其他项目（如用户模块需要登录），则
```
dependencies {

    if (runAsApp) {
        //模块化下需要引用到的模块
        addComponent 'module_login'
        ...
    }

}
```

用户模块完整例子：
```
apply from: rootProject.file('module.gradle')

android {

    defaultConfig {
        //仅在以application方式编译时才添加applicationId属性
        if (runAsApp) {
            applicationId build_version.applicationId + '.module_user'
        }
    }
    //统一规范资源名称前缀，防止多个 module 之间资源冲突
    resourcePrefix "user_"

}

dependencies {

    if (runAsApp) {
        //模块化下需要引用到的模块
        addComponent 'module_login'
        addComponent 'module_chat'
    }
}
```

`module.gradle` 详细请阅读代码即可，无需经常改动


### 工具 [最新版本](https://github.com/y1xian/What/releases) [![](https://jitpack.io/v/y1xian/What.svg)](https://jitpack.io/#y1xian/What)

```
  allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}

//要求项目使用Java 8
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}

dependencies {
    // 基于jetpack框架，实现快速开发、协同开发、实现可复用等
    implementation 'com.github.y1xian.What:lib_arch:+'
    // 适配器，封装paging 头尾部、增删查改
    implementation 'com.github.y1xian.What:lib_adapter:+'
    ···
    // 更多组件在widget下查看
    // 更多常用工具在utils下查看
}
```

### 其它说明

#### Gradle配置

 -	`module.gradle` 为业务模块配置的，每个业务模块都需配置
 -	`lib_util.gradle` 为工具类配置
 -	`lib_widget.gradle` 为组件类配置

#### 换肤
 -	新建模块，只需`res`包且对应资源名即可，可更换颜色，图片，shape。
 -	本项目中已实现`skin_night`夜间模式的皮肤，只需编译该模块，把生成的 `night.apk` 放进 `module_user` 用户模块即可，路径为 `assets/skins/night.apk`

### 易错点
 -	aar包的引用，整个项目运行会报错？
 -	包名申请第三方sdk，单模块下不成功？
 -	在哪混淆？
 -	就这？


---
## 总结
个人经验的积累，项目也会不断的完善
