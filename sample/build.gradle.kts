import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

val appVersionName = "1.0.0"

android {
    compileSdkVersion(Android.targetSdkVersion)

    defaultConfig {
        applicationId = "com.artemchep.cfg"

        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)

        versionCode = 2
        versionName = appVersionName
    }

    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":app"))

    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-alpha2")
}

