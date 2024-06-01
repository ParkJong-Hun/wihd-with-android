plugins {
    alias(libs.plugins.wihd.android)
    alias(libs.plugins.wihd.kotlin)
    alias(libs.plugins.wihd.compose)
    alias(libs.plugins.wihd.test)
    alias(libs.plugins.wihd.firebase)
    alias(libs.plugins.wihd.detekt)
    alias(libs.plugins.wihd.serialization)
}

android {
    namespace = "co.kr.parkjonghun.whatishedoingwithandroid.mobile"

    defaultConfig {
        applicationId = "co.kr.parkjonghun.whatishedoingwithandroid.mobile"
        versionCode = 1
        versionName = "0.2.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:login"))
    implementation(project(":feature:top"))
    implementation(project(":feature:news"))
    implementation(project(":feature:post"))
    implementation(project(":feature:profile"))
    implementation(project(":core:data:dao"))
    implementation(project(":core:data:interior"))
    implementation(project(":core:domain:base"))
    implementation(project(":core:domain:service"))
    implementation(project(":core:ui:system"))
    implementation(project(":core:ui:component"))

    implementation(libs.jetpack.core.splashscreen)
    implementation(libs.jetpack.navigation.compose)
    implementation(libs.jetpack.compose.material3.windowsizeclass)
    implementation(libs.timber)
}
