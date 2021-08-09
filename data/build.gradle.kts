import ModuleData.androidMaterialDesign
import ModuleData.androidXAppCompat
import ModuleData.androidXConstraintLayout
import ModuleData.androidXCore
import ModuleData.androidXTestEspresso
import ModuleData.androidXTestExt
import ModuleData.jUnit
import RandomUser.kotlinVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        minSdkVersion(27)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // region test
    testImplementation("junit:junit:$jUnit")
    androidTestImplementation("androidx.test.ext:junit:$androidXTestExt")
    // endregion test
}