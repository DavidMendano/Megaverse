# Megaverse App

## 🌌 Introduction

Megaverse is an Android application designed as part of a coding challenge.

## 🚀 Features

- **Interactive screen**: Basic interface to manage Megaverse objects
- **Error Handling**: Robust error handling with retry mechanisms
- **Modern Architecture**: Built with Clean Architecture and MVVM pattern
- **Jetpack Compose**: Responsive UI built with modern Android development tools

## 🛠️ Installation

### Prerequisites

- Android Studio Flamingo (2022.2.1) or newer
- Android SDK 33 or higher
- Kotlin 1.8.0 or higher
- Gradle 8.0 or higher

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/DavidMendano/Megaverse.git
   ```

2. Open the project in Android Studio:
   - Select "Open an Existing Project"
   - Navigate to the cloned directory and select it

3. Sync the project with Gradle files

4. Build and run the app on an emulator or physical device

## 🎮 Usage

1. **Create Objects**:
   - Click "Create Map" to generate objects at the specified coordinates
   - The app will show a loading indicator while processing

2. **Clear Objects**:
   - To remove an object, enter its coordinates and click "Clear"

## 🏗️ Architecture

The app follows Clean Architecture principles with the following layers:

- **Data Layer**: Handles data operations and API communication
- **Domain Layer**: Contains business logic and use cases
- **UI Layer**: Implements the user interface using Jetpack Compose

### Key Components

- **MVVM Pattern**: For separation of concerns and testability
- **Kotlin Coroutines**: For asynchronous programming
- **Koin**: For dependency injection
- **Retrofit**: For API communication
- **Jetpack Compose**: For modern, declarative UI

## ✨ Thank You!

Thank you for checking out Megaverse! We hope you enjoy using the app. If you have any questions or feedback, please don't hesitate to reach out.

Happy coding! 🚀