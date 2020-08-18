/**
 * 声明依赖
 */
object libs {
    // kotlin https://github.com/JetBrains/kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.kotlin_gradle}"
    const val kotlin_stdlib_common = "org.jetbrains.kotlin:kotlin-stdlib-common:${versions.kotlin_gradle}"

    // 协程    https://github.com/Kotlin/kotlinx.coroutines
    const val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.kotlin_coroutines}"
    const val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.kotlin_coroutines}"

    const val appcompat = "androidx.appcompat:appcompat:${versions.appcompat}"
    const val core_ktx = "androidx.core:core-ktx:${versions.core_ktx}"
    const val fragment = "androidx.fragment:fragment:${versions.fragment}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${versions.recyclerview}"
    const val cardview = "androidx.cardview:cardview:${versions.cardview}"
    const val annotations = "androidx.annotation:annotation:${versions.annotation}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${versions.constraint}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${versions.viewpager2}"

    //应用启动 https://developer.android.google.cn/topic/libraries/app-startup
    const val startup = "androidx.startup:startup-runtime:${versions.startup}"

    //生命周期感知型组件 https://developer.android.google.cn/topic/libraries/architecture/lifecycle
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${versions.lifecycle}"
    const val lifecycle_java8 = "androidx.lifecycle:lifecycle-common-java8:${versions.lifecycle}"
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${versions.lifecycle}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel:${versions.lifecycle}"
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata:${versions.lifecycle}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime:${versions.lifecycle}"

    //导航 https://developer.android.google.cn/guide/navigation
    const val navigation_runtime = "androidx.navigation:navigation-runtime:${versions.navigation}"
    const val navigation_common = "androidx.navigation:navigation-common:${versions.navigation}"
    const val navigation_common_ktx = "androidx.navigation:navigation-common-ktx:${versions.navigation}"
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${versions.navigation}"

    //异步任务 https://developer.android.google.cn/topic/libraries/architecture/workmanager
    const val work = "androidx.work:work-runtime:${versions.work}"

    //分页库 https://developer.android.google.cn/topic/libraries/architecture/paging
    const val paging = "androidx.paging:paging-runtime:${versions.paging}"
    const val paging_rxjava = "androidx.paging:paging-rxjava2:${versions.paging}"

    //数据库 https://developer.android.google.cn/topic/libraries/architecture/room
    const val room = "androidx.room:room-runtime:${versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${versions.room}"
    const val room_rxjava = "androidx.room:room-rxjava2:${versions.room}"
    const val room_test = "androidx.room:room-testing:${versions.room}"

    //阿里路由框架    https://github.com/alibaba/ARouter
    const val arouter_api = "com.alibaba:arouter-api:1.5.0"
    const val arouter_compiler = "com.alibaba:arouter-compiler:1.2.2"

    //glide     https://github.com/bumptech/glide
    const val glide = "com.github.bumptech.glide:glide:${versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${versions.glide}"

    //https://github.com/square/okhttp
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${versions.okhttp}"
    const val okhttp3_logging = "com.squareup.okhttp3:logging-interceptor:${versions.okhttp}"

    //retrofit      https://github.com/square/retrofit
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
    const val retrofit2_gson = "com.squareup.retrofit2:converter-gson:${versions.retrofit}"
    const val retrofit2_rxjava = "com.squareup.retrofit2:adapter-rxjava2:${versions.retrofit}"

    //rxjava2
    // https://github.com/ReactiveX/RxJava
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${versions.rxjava2}"

    //  https://github.com/ReactiveX/RxAndroid
    const val rxandroid2 = "io.reactivex.rxjava2:rxandroid:${versions.rxandroid2}"

    // rxjava3
    const val rxjava3 = "io.reactivex.rxjava3:rxjava:${versions.rxjava3}"
    const val rxandroid3 = "io.reactivex.rxjava3:rxandroid:${versions.rxandroid3}"
    const val rxbinding3 = "com.jakewharton.rxbinding3:rxbinding:${versions.rxbinding3}"

    //  https://github.com/tbruyelle/RxPermissions
    const val rxpermissions = "com.github.tbruyelle:rxpermissions:0.10.2'}"

    const val rximagepicker = "com.github.qingmei2:rximagepicker:2.3.0-alpha03'}"
    const val rxweaver = "com.github.qingmei2.rxweaver:rxweaver:0.3.0'}"

    //  https://github.com/JakeWharton/RxBinding
    const val rxbinding2 = "com.jakewharton.rxbinding2:rxbinding:${versions.rxbinding2}"
    const val rxbinding2_v7 = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7:${versions.rxbinding2}"

    //  https://github.com/uber/AutoDispose
    const val autodispose = "com.uber.autodispose:autodispose-android:${versions.autodispose}"
    const val autodispose_ktx = "com.uber.autodispose:autodispose-ktx:${versions.autodispose}"
    const val autodispose_android_ktx = "com.uber.autodispose:autodispose-android-ktx:${versions.autodispose}"
    const val autodispose_archs = "com.uber.autodispose:autodispose-android-archcomponents:${versions.autodispose}"
    const val autodispose_archs_ktx = "com.uber.autodispose:autodispose-android-archcomponents-ktx:${versions.autodispose}"

    //内存泄漏  https://github.com/square/leakcanary
    const val leak_canary_debug = "com.squareup.leakcanary:leakcanary-android:${versions.leakcanary}"

    //https://github.com/google/gson
    const val gson = "com.google.code.gson:gson:${versions.gson}"

    //测试 https://developer.android.com/training/testing/junit-rules
    const val junit = "junit:junit:${versions.junit}"
    const val junitx = "androidx.test.ext:junit:1.1.1'}"

    //测试 https://developer.android.com/training/testing/junit-runner
    const val test_runner = "androidx.test:runner:1.2.0'}"
    const val orchestrator = "androidx.test:orchestrator:1.1.0'}"

    //方法数超过64K https://developer.android.com/studio/build/multidex
    const val multidex = "androidx.multidex:multidex:2.0.1'}"

    //单元测试
    const val robolectric = "org.robolectric:robolectric:${versions.robolectric}"
    const val robolectric_v4 = "org.robolectric:shadows-support-v4:${versions.robolectric}"

    //单元测试  https://developer.android.com/training/testing/espresso
    const val espresso_core = "androidx.test.espresso:espresso-core:${versions.espresso}"

    //微信 键值对储存 https://github.com/Tencent/MMKV
    const val mmkv = "com.tencent:mmkv:${versions.mmkv}"
    const val mmkv_static = "com.tencent:mmkv-static:${versions.mmkv}"

    //骨架 https://github.com/ethanhua/Skeleton
    const val skeleton = "com.ethanhua:skeleton:1.1.2"
    const val shimmerlayout = "io.supercharge:shimmerlayout:2.1.0"

    //事件总线 https://github.com/JeremyLiao/LiveEventBus
    const val live_event_bus = "com.jeremyliao:live-event-bus-x:1.7.2"

    //侧滑 https://github.com/anzewei/ParallaxBackLayout
    const val parallaxbacklayout = "com.github.anzewei:parallaxbacklayout:1.1.9"

    //侧滑 https://github.com/luckybilly/SmartSwipe
    const val swipe = "com.billy.android:smart-swipe:1.1.2"
    const val swipe_support = "com.billy.android:smart-swipe-support:1.1.0"

    //屏幕适配方案 https://github.com/JessYanCoding/AndroidAutoSize
    const val autosize = "me.jessyan:autosize:1.2.1"

    //https://github.com/y1xian/Widget
    const val widget_common = "com.github.y1xian.Widget:lib_common:${versions.widget}"
    const val widget_adapter = "com.github.y1xian.Widget:lib_adapter:${versions.widget}"
    const val widget_view = "com.github.y1xian.Widget:lib_view:${versions.widget}"
    const val widget_utils = "com.github.y1xian.Widget:lib_utils:${versions.widget}"
    const val widget_skin = "com.github.y1xian.Widget:lib_skin:${versions.widget}"
    const val widget_skinloader = "com.github.y1xian.Widget:lib_skinloader:${versions.widget}"
}