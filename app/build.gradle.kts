import ModuleApp.androidMaterialDesignVersion
import ModuleApp.androidXAppCompatVersion
import ModuleApp.androidXConstraintLayoutVersion
import ModuleApp.androidXCoreVersion
import ModuleApp.glideVersion
import ModuleApp.koinVersion
import ModuleApp.androidRecyclerViewVersion
import ModuleApp.androidXLifeCycle
import ModuleApp.androidXNavigation
import RandomUser.kotlinVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
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

    // https://developer.android.com/topic/libraries/view-binding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // region koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    // endregion koin

    // region androidx
    implementation("androidx.core:core-ktx:$androidXCoreVersion")
    implementation("androidx.appcompat:appcompat:$androidXAppCompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:$androidXConstraintLayoutVersion")
    implementation("androidx.recyclerview:recyclerview:$androidRecyclerViewVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidXLifeCycle")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$androidXLifeCycle")
    implementation("androidx.navigation:navigation-fragment-ktx:$androidXNavigation")
    implementation("androidx.navigation:navigation-ui-ktx:$androidXNavigation")
    // endregion androidx

    // region androidMaterialDesign
    implementation("com.google.android.material:material:$androidMaterialDesignVersion")
    // endregion androidMaterialDesign

    // region glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    // endregion glide
}
