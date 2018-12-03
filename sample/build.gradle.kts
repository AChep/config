import org.jetbrains.kotlin.config.KotlinCompilerVersion
import kotlin.math.max

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

val appVersionName = "1.0.0"
val appDependencies = createDependencies(Module.SAMPLE)

android {
    compileSdkVersion(Android.targetSdkVersion)

    defaultConfig {
        applicationId = "com.artemchep.cfg"

        minSdkVersion(max(Android.minSdkVersion, 23))
        targetSdkVersion(Android.targetSdkVersion)

        versionCode = 2
        versionName = appVersionName
    }

    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }

        // Convert dependencies to java code, to
        // show them later in the app.
        val (bcFieldType, bcFieldValue) = appDependencies.toJavaField()
        forEach { buildType ->
            buildType.buildConfigField(bcFieldType, "DEPENDENCIES", bcFieldValue)
        }
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":app"))
    handle(this, appDependencies)
}

