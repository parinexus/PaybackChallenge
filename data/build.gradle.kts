plugins {
    id(libs.plugins.android.library.get().toString())
    id(libs.plugins.kotlin.android.get().toString())
    id(libs.plugins.ksp.get().toString())
}

android {
    compileSdk = libs.versions.compile.sdk.get().toInt()
    namespace = "com.pixabay.challenge.data"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"${System.getenv("BASE_URL")}\"")
        buildConfigField("String", "API_KEY", "\"${System.getenv("API_KEY")}\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    // Storage (Room)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // OkHttp3
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Testing dependencies
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.core.testing)
}