fun createDependencies(module: Module): List<Dependency> {
    val kotlinStdlib = Dependency(
        "Kotlin StdLib",
        KOTLIN_VERSION,
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION",
        DependencyType.IMPLEMENTATION
    )
    val kluent = Dependency(
        "Kluent",
        KLUENT_VERSION,
        "org.amshove.kluent:kluent-android:$KLUENT_VERSION",
        DependencyType.TEST_IMPLEMENTATION
    )

    val androidxAppCompat = Dependency(
        "AndroidX AppCompat",
        ANDROIDX_APPCOMPAT_VERSION,
        "androidx.appcompat:appcompat:$ANDROIDX_APPCOMPAT_VERSION",
        DependencyType.IMPLEMENTATION
    )
    val androidxKtx = Dependency(
        "AndroidX KTX",
        ANDROIDX_KTX_VERSION,
        "androidx.core:core-ktx:$ANDROIDX_KTX_VERSION",
        DependencyType.IMPLEMENTATION
    )
    val androidxConstraintLayout = Dependency(
        "AndroidX Constraint Layout",
        ANDROIDX_CONSTRAINTLAYOUT_VERSION,
        "androidx.constraintlayout:constraintlayout:$ANDROIDX_CONSTRAINTLAYOUT_VERSION",
        DependencyType.IMPLEMENTATION
    )

    val googleMaterial = Dependency(
        "Google Material",
        GOOGLE_MATERIAL_VERSION,
        "com.google.android.material:material:$GOOGLE_MATERIAL_VERSION",
        DependencyType.IMPLEMENTATION
    )

    return when (module) {
        Module.LIBRARY -> listOf(
            kotlinStdlib,
            kluent
        )
        Module.SAMPLE -> listOf(
            kotlinStdlib,
            androidxAppCompat,
            androidxKtx,
            androidxConstraintLayout,
            googleMaterial
        )
    }
}