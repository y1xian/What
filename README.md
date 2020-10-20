# What  

该框架基于 Jetpack + Arouter + Retrofit + MVVM等实现的一款开源学习项目，该框架存在的意义一方面是为了能实现快速开发、协同开发、实现可复用等，另一方面是想把自己会的、有趣的、可复用的东西写出来，分Java、Kotlin版本

## [流程图 - 项目业务开发方向](https://www.processon.com/view/link/5ee9e48407912929cb49f28d)

### [组件化流程图 - 大概](https://www.processon.com/view/link/5ee9d5bdf346fb1ae569847d)



#### 项目
-  主要使用了 JitPack 、 ARouter 、RxJava2 、Retrofit2 等主流框架，MVVM模式
-  主工程运行、独立运行
-  各模块相互跳转
-  功能业务分离解耦


#### 框架
-  底层框架：集成JetPack，解决单继承问题，只需实现`IActivity`、`IFragment` 和 部分注解 `@BindRes`、`@BindViewModel`等
-  网络请求：
    - java： `Retrofit` + `RxJava` + `Okhttp`
    - kotlin：`Retrofit` + `Okhttp` + `Coroutine`
-  适配器：封装 `Paging` 头尾、增删查改
-  自行查看`widget`目录


开发环境：Android Studio 4.1 、gradle 6.6 、kotlin 1.4.10

- 最新Java + support版本的看 [dev-arch](https://github.com/y1xian/What/tree/dev-arch) 分支

- 最新AndroidX + Kotlin版本的看 [kt-x-arch](https://github.com/y1xian/What/tree/kt-x-arch) 分支，【kotlin版本会比java版本更新慢，重心还在java】



### 组件化单项目运行
1. 在 `local.properties` 下添加
```
#module_login=true
module_wanandroid=true
module_user=true
...
```
之后build一下

2. 如在单项目需要引用到其他项目，则
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

`module.gradle`详细请阅读代码即可，无需经常改动


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
    // 更多工具在widget下查看
}
```

### 易错点
- aar包的引用，整个项目运行会报错？
- 包名申请第三方sdk，单模块下不成功？
- 在哪混淆？
- ？

---
## 总结
个人经验的积累，项目也会不断的完善

