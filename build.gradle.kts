buildscript {
    extra["compose_version"] = "1.3.0"
}

plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

tasks.register<Delete>("PaybackChallenge") {
    delete(rootProject.buildDir)
}