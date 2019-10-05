object Config {
    // Android config
    const val androidMinSdk = 16
    const val androidTargetSdk = 28
    const val androidCompileSdk = 28
}

object Versions {
    // Kotlin
    const val kotlin = "1.3.50"

    // Plugins
    const val sonarqubePlugin = "2.8"
    const val androidMavenPlugin = "2.1"
    const val androidToolsPlugin = "3.5.1"
    const val versionsPlugin = "0.25.0"

    // Libs
    const val androidxLifecycle = "2.0.0"
    const val material = "1.0.0"
    const val appCompat = "1.0.2"

    const val easyRetrofit = "1.1.0.0"
    const val easyRecyclerview = "1.0.1"
    const val spinnableLib = "1.1.0.2"
    const val proWebview = "2.2.1"
    const val coroutinesAndroid = "1.3.0"
    const val timber = "4.7.1"
}

object Plugins {
    const val kotlin = "gradle-plugin"
    const val versions = "com.github.ben-manes.versions"
    const val androidTools = "com.android.tools.build:gradle:${Versions.androidToolsPlugin}"
    const val androidApp = "com.android.application"
    const val androidLib = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kapt = "kapt"
    const val sonarqube = "org.sonarqube"
    const val androidMaven = "com.github.dcendents.android-maven"
}

object Libs {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.androidxLifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.androidxLifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidxLifecycle}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

    const val easyRetrofit = "com.github.pedrofsn:EasyRetrofit:${Versions.easyRetrofit}"
    const val easyRecyclerview = "com.github.pedrofsn:EasyRecyclerview:${Versions.easyRecyclerview}"
    const val spinnable = "com.github.pedrofsn:spinnable:${Versions.spinnableLib}"
    const val proWebview = "com.github.vic797:prowebview:${Versions.proWebview}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}