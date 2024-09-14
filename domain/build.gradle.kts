plugins {
    id(libs.plugins.kotlin.plugin.get().toString())
}

dependencies {
    implementation(project(":common"))

    implementation(libs.coroutines.android)
    implementation (libs.javax.inject)
}