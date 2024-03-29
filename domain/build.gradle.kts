import ModuleDomain.coroutinesVersion
import ModuleDomain.googleTruthVersion
import ModuleDomain.koinVersion
import ModuleDomain.kotlinXCoroutinesTestVersion
import ModuleDomain.mockkVersion
import RandomUser.kotlinVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // region koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    // endregion koin

    // region coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    // endregion coroutines

    // region test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinXCoroutinesTestVersion")
    implementation("com.google.truth:truth:$googleTruthVersion")
    implementation("io.mockk:mockk:$mockkVersion")
    // endregion test
}