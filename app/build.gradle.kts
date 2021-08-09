import ModuleApp.androidMaterialDesign
import ModuleApp.androidXAppCompat
import ModuleApp.androidXConstraintLayout
import ModuleApp.androidXCore
import ModuleApp.androidXTestEspresso
import ModuleApp.androidXTestExt
import ModuleApp.jUnit
import RandomUser.kotlinVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId = "com.sermarmu.randomuser"
        minSdkVersion(27)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
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

    // https://developer.android.com/topic/libraries/view-binding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")


    // region androidx
    implementation("androidx.core:core-ktx:$androidXCore")
    implementation("androidx.appcompat:appcompat:$androidXAppCompat")
    implementation("androidx.constraintlayout:constraintlayout:$androidXConstraintLayout")
    // endregion androidx

    // region androidMaterialDesign
    implementation("com.google.android.material:material:$androidMaterialDesign")
    // endregion androidMaterialDesign

    // region test
    testImplementation("junit:junit:$jUnit")
    androidTestImplementation("androidx.test.ext:junit:$androidXTestExt")
    androidTestImplementation("androidx.test.espresso:espresso-core:$androidXTestEspresso")
    // endregion test
}
