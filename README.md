# Pixabay Image Search and Details

## Overview

This Android application enables users to search for images using the Pixabay API and view detailed information about selected images. The app provides a clean and responsive UI built with Jetpack Compose, manages dependencies with Hilt, and leverages modern libraries for networking and image loading.

## Features

- **Image Search**: Users can search for images from Pixabay.
- **Image Thumbnail**: Displays a grid of image thumbnails.
- **Image Details**: Clicking on a thumbnail shows a dialog box asking for user confirmation to view details.
- **Offline Caching**: Server responses are cached in storage for offline access.
- **Default Search**: On app startup, a default search query is performed to populate the initial image list.
- **Detailed View**: Upon user confirmation, the app navigates to a detail screen showcasing the selected image.

## Dependencies

The project utilizes the following dependencies:

### Compose UI
- `androidx.compose.runtime:runtime:1.3.3`
- `androidx.compose.ui:ui:1.3.3`
- `androidx.compose.material:material:1.3.1`
- `androidx.activity:activity-compose:1.6.1`
- `androidx.core:core-splashscreen:1.1.0-rc01`
- `com.google.accompanist:accompanist-flowlayout:0.29.2-rc`

### Room Database
- `androidx.room:room-runtime:2.5.0`
- `androidx.room:room-ktx:2.5.0`
- `kapt "androidx.room:room-compiler:2.5.0"`

### Hilt Dependency Injection
- `com.google.dagger:hilt-android:2.42`
- `androidx.hilt:hilt-navigation-compose:1.0.0`
- `kapt 'com.google.dagger:hilt-android-compiler:2.42'`
- `kapt "androidx.hilt:hilt-compiler:1.0.0"`

### Coroutines
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4`

### Retrofit for Networking
- `com.squareup.retrofit2:retrofit:2.9.0`
- `com.squareup.retrofit2:converter-gson:2.9.0`

### OkHttp3
- `com.squareup.okhttp3:okhttp-bom:4.9.1`
- `com.squareup.okhttp3:okhttp`
- `com.squareup.okhttp3:logging-interceptor`

### Navigation
- `androidx.navigation:navigation-compose:2.5.3`

### Image Loading
- `io.coil-kt:coil-compose:2.1.0`
- `io.coil-kt:coil-svg:2.1.0`

### Testing
- `testImplementation "junit:junit:4.13.2"`
- `testImplementation "org.mockito:mockito-core:5.0.0"`
- `testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4'`
- `testImplementation "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"`
- `testImplementation "androidx.arch.core:core-testing:2.2.0"`

### Instrumented Testing
- `androidTestImplementation 'androidx.test.ext:junit:1.1.5'`
- `androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"`

## Usage

1. **Search Images**: On app launch, a default search query is executed. Users can modify the query to search for different images.
2. **View Thumbnails**: The app displays thumbnails of the searched images.
3. **Image Details**: Clicking a thumbnail brings up a dialog box for user confirmation. If confirmed, the app navigates to a detailed view of the selected image.

## Next Steps

- **Add Chucker**: Integrate Chucker to visualize and inspect network requests and responses.
- **Enhance Testing**: Expand test coverage to ensure robust application functionality and reliability.
- **Migrate to Kotlin Script (KTS)**: Refactor the Gradle build files to Kotlin Script (KTS) for better type safety and tooling support. Organize dependencies in a centralized manner to improve maintainability.
- **Adopt Kotlin Multiplatform Mobile (KMM)**: Leverage Kotlin Multiplatform Mobile (KMM) to share code between Android and iOS, specifically focusing on business logic and network communication.
- **Integrate Ktor**: Replace Retrofit with Ktor for network requests, providing a more consistent experience across multiple platforms when using KMM.

## Contribution

Feel free to fork the repository and submit pull requests. Contributions, feedback, and suggestions are welcome!