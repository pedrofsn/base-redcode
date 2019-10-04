plugins {
    id(Plugins.androidLib)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinAndroidExtensions)
    kotlin(Plugins.kapt)
    id(Plugins.sonarqube)
    id(Plugins.androidMaven)
}

group = "com.github.pedrofsn"

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(Config.androidCompileSdk)

    defaultConfig {
        minSdkVersion(Config.androidMinSdk)
        targetSdkVersion(Config.androidTargetSdk)
        versionCode = 1
        versionName = "1.0"
        resConfig("pt")

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    packagingOptions {
        exclude("META-INF/rxjava.properties")
    }
}

dependencies {
    implementation(Libs.kotlinStdLib)
    implementation(Libs.material)
    api(Libs.appCompat)

    api(Libs.spinnable)
    api(Libs.timber)
}

sonarqube {
    properties {
        property("sonar.projectName", "base-code-base")
        property("sonar.projectKey", "aaa")
        property("sonar.host.url", "aaa")
        property("sonar.language", "kotlin")
        property("sonar.sources", "src/main/")
        property("sonar.login", "aaa")
        property("sonar.password", "aaa")
    }
}
