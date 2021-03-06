import org.jetbrains.kotlin.config.KotlinCompilerVersion

import com.novoda.gradle.release.PublishExtension

plugins {
    id("com.novoda.bintray-release")
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

val appVersionName = "2.2.0"
val appDependencies = createDependencies(Module.LIBRARY)

configure<PublishExtension> {
    setLicences("Apache-2.0")

    userOrg = "achep"
    repoName = "maven"

    groupId = "com.artemchep.config"
    artifactId = "config"
    publishVersion = appVersionName
    desc =
            "Utility library for Android with Kotlin to help you to create and manage simple settings of application."
    website = "https://github.com/achep/config"
}

android {
    compileSdkVersion(Android.targetSdkVersion)

    defaultConfig {
        minSdkVersion(Android.minSdkVersion)
        targetSdkVersion(Android.targetSdkVersion)

        versionCode = 5
        versionName = appVersionName

        setProperty("archivesBaseName", "config")
    }

    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
        }

        maybeCreate("debug").apply {
            isMinifyEnabled = false
        }

        // Convert dependencies to java code, to
        // show them later in the app.
        val (bcFieldType, bcFieldValue) = appDependencies.toJavaField()
        forEach { buildType ->
            buildType.buildConfigField(bcFieldType, "DEPENDENCIES", bcFieldValue)
        }
    }
}

dependencies {
    handle(this, appDependencies)
}
