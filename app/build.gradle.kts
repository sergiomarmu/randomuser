import ModuleApp.androidMaterialDesignVersion
import ModuleApp.androidRecyclerViewVersion
import ModuleApp.androidXAppCompatVersion
import ModuleApp.androidXConstraintLayoutVersion
import ModuleApp.androidXCoreVersion
import ModuleApp.androidXEspressoCoreVersion
import ModuleApp.androidXFragmentVersion
import ModuleApp.androidXLifeCycle
import ModuleApp.androidXNavigation
import ModuleApp.androidXNavigationTesting
import ModuleApp.glideVersion
import ModuleApp.googleTruthVersion
import ModuleApp.koinVersion
import ModuleApp.kotlinXCoroutinesTestVersion
import ModuleApp.mockitoVersion
import ModuleApp.mockkVersion
import ModuleApp.okkHttpVersion
import ModuleCore.coroutinesVersion
import ModuleData.androidXJunitVersion
import RandomUser.kotlinVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

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

    // region test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinXCoroutinesTestVersion")
    implementation("com.google.truth:truth:$googleTruthVersion")
    implementation("io.mockk:mockk:$mockkVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidXJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$androidXEspressoCoreVersion")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:$androidXEspressoCoreVersion")
    debugImplementation("androidx.fragment:fragment-testing:$androidXFragmentVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$androidXNavigationTesting")
    // It's needed when we called our exceptions in Data
    implementation("com.squareup.okhttp3:okhttp:$okkHttpVersion")
    // https://site.mockito.org/
    androidTestImplementation("org.mockito:mockito-android:$mockitoVersion")
    // endregion test
}
