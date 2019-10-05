plugins {
    id(Plugins.androidApp)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinAndroidExtensions)
    kotlin(Plugins.kapt)
}

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(Config.androidCompileSdk)

    defaultConfig {
        applicationId = "br.com.redcode.base.sample"
        minSdkVersion(Config.androidMinSdk)
        targetSdkVersion(Config.androidTargetSdk)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(Libs.kotlinStdLib)
    implementation(project(":mvvm-restful"))
}
