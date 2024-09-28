plugins {
    id(libs.plugins.android.application.get().toString())
    id(libs.plugins.kotlin.android.get().toString())
    id(libs.plugins.hilt.android.plugin.get().toString())
    id(libs.plugins.kotlin.parcelize.get().toString())
    id(libs.plugins.google.hilt.get().toString())
    id(libs.plugins.kapt.get().toString())
}

android {
    compileSdk = libs.versions.compile.sdk.get().toInt()
    namespace = "com.pixabay.challenge"

    defaultConfig {
        applicationId = "com.pixabay.challenge"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "${project.properties["BASE_URL"]}")
        buildConfigField("String", "API_KEY", "${project.properties["API_KEY"]}")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                file("proguard-rules.pro")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Compose
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.accompanist.flowlayout)

    // Storage (Room)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // OkHttp3
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Compose Navigation
    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Testing dependencies
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.core.testing)

    // For instrumented tests
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
}