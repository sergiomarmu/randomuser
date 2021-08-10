buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${RandomUser.kotlinGradlePluginVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${RandomUser.kotlinVersion}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${RandomUser.navVersion}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}