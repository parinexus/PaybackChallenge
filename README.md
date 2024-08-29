# Pixabay Image Search and Details

## Overview

This Android application enables users to search for images using the Pixabay API and view detailed information about selected images. The app provides a clean and responsive UI built with Jetpack Compose, manages dependencies with Hilt, and leverages modern libraries for networking and image loading. It supports configuration changes and adapts to both light and dark modes seamlessly. UI tests have been integrated to ensure a consistent and reliable user experience.

## Features

- **Image Search**: Users can search for images from Pixabay.
- **Image Thumbnail**: Displays a grid of image thumbnails.
- **Image Details**: Clicking on a thumbnail shows a dialog box asking for user confirmation to view details.
- **Offline Caching**: Server responses are cached in storage for offline access.
- **Default Search**: On app startup, a default search query with the term “fruits” is performed to populate the initial image list.
- **Detailed View**: Upon user confirmation, the app navigates to a detail screen showcasing the selected image.
- **Configuration Changes**: The app efficiently handles configuration changes, such as screen rotations, ensuring that user state is preserved and the UI is updated accordingly.
- **Night/Light Mode Support**: The app adapts to the system's light or dark mode settings, providing a consistent and visually appealing experience across different themes.
- **UI Testing**: UI tests are included to validate the interface's functionality and ensure that it behaves correctly under various conditions.

## Dependencies

Here’s a summary of the key dependencies used in the project:

- **Jetpack Compose**: Used for building the UI. It enables a declarative way of designing UIs, allowing you to define how the UI should look and behave based on the app's state.

- **Room**: A persistence library that provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. It is used for offline caching of data.

- **Hilt**: A dependency injection library that simplifies the process of providing dependencies in Android applications. Hilt is used in this project to manage dependencies like network clients, repositories, and other components.

- **Kotlin Coroutines**: Used for managing background tasks and asynchronous operations. Coroutines simplify asynchronous programming by providing a more readable and maintainable way to handle tasks like network requests or database operations.

- **Retrofit**: A type-safe HTTP client used for making network requests to the Pixabay API. Retrofit simplifies the process of connecting to REST APIs and handling responses.

- **OkHttp**: A powerful HTTP client that works with Retrofit. It's used for making network requests, with an added logging interceptor for debugging purposes.

- **Navigation Component**: This handles in-app navigation and provides a framework for navigating between different screens in the app.

- **Coil**: An image loading library used to load and display images from the web. Coil is optimized for Android and integrates well with Jetpack Compose.

- **JUnit and Mockito**: Testing libraries used for writing unit tests and mocking dependencies to ensure the correctness of the application logic.

- **Kotlin Multiplatform Mobile (KMM)** (planned): To share code between Android and iOS, particularly for business logic and network communication.

- **Ktor** (planned): A framework for making HTTP requests, which will be used in place of Retrofit to provide consistency across multiple platforms when adopting KMM.

## Usage

1. **Search Images**: On app launch, a default search query with the term “fruits” is executed. Users can modify the query to search for different images.
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