plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.mylrc.mymusic"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.mylrc.mymusic"
        minSdk = 24
        targetSdk = 36
        versionName = "4.1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
        versionCode = 4150
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            multiDexEnabled = false
        }
        getByName("debug") {
            isJniDebuggable = true
            isRenderscriptDebuggable = false
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            multiDexEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.okhttp3)
    implementation(libs.jaudiotagger)
    implementation(libs.imageloader)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}