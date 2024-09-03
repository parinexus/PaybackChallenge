plugins {
    id(libs.plugins.android.library.get().toString())
    id(libs.plugins.kotlin.android.get().toString())
}

android {
    compileSdk = libs.versions.compile.sdk.get().toInt()
    namespace = "com.pixabay.challenge.common"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}