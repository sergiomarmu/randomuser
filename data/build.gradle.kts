import ModuleData.coroutinesVersion
import ModuleData.gsonVersion
import ModuleData.koinVersion
import ModuleData.loggingInterceptorVersion
import ModuleData.retrofit2ConverterGsonVersion
import ModuleData.retrofit2Version
import ModuleData.roomVersion
import RandomUser.kotlinVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
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
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // region koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    // endregion koin

    // region gson
    implementation("com.google.code.gson:gson:$gsonVersion")
    // endregion gson

    // region retrofit
    // see https://square.github.io/retrofit/
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit2ConverterGsonVersion")
    // endregion retrofit

    // region coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    // endregion coroutines


    // region room
    // see https://developer.android.com/training/data-storage/room
    api("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Kotlin Extensions and Coroutines support for Room
    api("androidx.room:room-ktx:$roomVersion")
    // Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")
    // endregion room
}