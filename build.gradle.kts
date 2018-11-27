buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.4.0-alpha05")
        classpath("com.novoda:bintray-release:0.9")
        classpath(kotlin("gradle-plugin", version = "1.3.10"))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
