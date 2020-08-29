# What  

Android组件化练手项目

## [流程图 - 项目业务开发方向](https://www.processon.com/view/link/5ee9e48407912929cb49f28d)

-  主要使用了 JitPack 、 ARouter 、RxJava2 、Retrofit2 等主流框架，MVVM模式
-  主工程运行、独立运行
-  各模块相互跳转
-  功能业务分离解耦


Android Studio 4.0 、gradle 6.2 、kotlin 1.4.0

最新Java + support版本的看 [dev-arch](https://github.com/y1xian/What/tree/dev-arch) 分支

最新AndroidX + Kotlin版本的看 [kt-x-arch](https://github.com/y1xian/What/tree/kt-x-arch) 分支



### 组件化单项目运行
1. 在 `local.properties` 下添加
```
module_login=true
module_wanandroid=true
#module_user=true
```
之后build一下
2.如在单项目需要引用到其他项目，则
```
dependencies {

    if (runAsApp) {
        //模块化下需要引用到的模块
        addComponent 'module_user'
        addComponent 'module_login'
    }

}
```
详细参考各module即可

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
    // 框架
    implementation 'com.github.y1xian.What:lib_arch:+'
    // 适配器
    implementation 'com.github.y1xian.What:lib_adapter:+'
    ···
    // 更多工具在widget下查看
}
```

