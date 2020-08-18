/**
 * 项目build
 */
object build_plugins {
    const val android_gradle = "com.android.tools.build:gradle: ${versions.android_plugin}"
    const val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin: ${versions.kotlin_gradle}"

    //https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
    const val aspectjx_gradle = "com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.8"
}