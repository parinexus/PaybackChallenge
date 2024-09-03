plugins {
    id(
        libs.plugins.android.application.get().toString()
    ) version libs.versions.android.plugin.get() apply false
    id(
        libs.plugins.android.library.get().toString()
    ) version libs.versions.android.plugin.get() apply false
    id(
        libs.plugins.google.hilt.get().toString()
    ) version libs.versions.dagger.hilt.android.get() apply false
    id(
        libs.plugins.kotlin.android.get().toString()
    ) version libs.versions.android.kotlin.get() apply false

    id(libs.plugins.ksp.get().toString()) version libs.versions.ksp.version.get() apply false
}

tasks.register<Delete>("PixabayImageSearch") {
    delete(project.layout.buildDirectory.asFile)
}