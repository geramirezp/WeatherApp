
# Weather App

The Weather app is a mobile application designed for Android devices that provides users the weather information for various locations. Developed using modern Android technologies and architectural patterns, the app offers a seamless user experience combined with robust functionality.


# Development Environment

**Weather app** uses the Gradle build system and can be imported directly into IntelliJ IDEA or Android Studio.

Change the run configuration to `app`.

![image](https://user-images.githubusercontent.com/873212/210559920-ef4a40c5-c8e0-478b-bb00-4879a8cf184a.png)

The `debug` and `release` build variants can be built and run.


Once you're up and running, you can explore the app to see the behavior and performance that are the result of implement different Android technologies and architectural patterns and the best approaches to
UI, testing, and more.

# Build

The app contains the usual `debug` and `release` build variants.

Plasea follow the next intrusions to build and run the Weather app

**Select Debug Variant:** In Android Studio or IntelliJ IDEA , navigate to the "Build Variants" tab in the bottom-left corner of the window. From the "Active Build Variant" dropdown menu, select the "debug" variant.

**Connect Device:** Connect your Android device to your computer using a USB cable. Ensure that USB debugging is enabled on your device. You can enable this option in the device's Developer Options menu.

**Run Configuration:** In Android Studio or IntelliJ IDEA , select the "app" module from the project navigator. Then, click on the green play button in the toolbar (or press Shift + F10) to build and run your app.

**Select Target Device:** If you have multiple devices connected to your computer, Android Studio will prompt you to select the target device for deployment. Choose your desired device from the list.

**Deployment and Installation:** Android Studio or IntelliJ IDEA will compile your app and deploy the debug version to the selected device. You can monitor the progress in the "Run" window at the bottom of the IDE.


# Main technologies

Jetpack Compose: The user interface of the app is developed using Jetpack Compose, a modern UI toolkit for building native Android apps. Compose offers a declarative and reactive approach to UI development, making it easier to create dynamic and responsive user interfaces.

Coroutines and Flow: Asynchronous programming in the app is handled using Kotlin Coroutines and Flow. Coroutines simplify asynchronous code execution and concurrency management, while Flow provides a streamlined way to handle asynchronous data streams, such as network requests and database queries.

Dagger 2 Dependency Injection: Dependency injection is utilized throughout the app using Dagger 2 framework. By injecting dependencies at runtime, Dagger 2 simplifies the management of object dependencies and promotes modularization and decoupling of components.

Clean Architecture: The app follows the principles of Clean Architecture, ensuring a clear separation of concerns between presentation logic, business logic, and data access layers. This architectural pattern promotes scalability, maintainability, and testability of the codebase.

MVVM Design Pattern: Built on the Model-View-ViewModel (MVVM) design pattern, the app separates the user interface (View) from the underlying data (Model) and business logic (ViewModel). This architecture facilitates better code organization, easier testing, and improved code reusability.