# User Data Manager Application

This is a native Android application developed in Java that demonstrates modern Android development best practices. It features user authentication, offline data persistence, networking, dynamic theme management, and web content integration.

## Features

### 1. Authentication & Security
- **Login Screen**: Secure-feeling login interface with validation.
- **Session Management**: Persistent login state using `SharedPreferences`.
- **Auto-Login**: Automatically redirects authenticated users to the main dashboard.

### 2. Networking & Data
- **REST API Integration**: Fetches user data from [JSONPlaceholder](https://jsonplaceholder.typicode.com/) using **Retrofit 2**.
- **Offline Support**: Caches API data locally using **SQLite** (`SQLiteOpenHelper`) for offline access.
- **Data Sync**: Automatically updates the local database with fresh data from the API when online.

### 3. UI/UX & Theming
- **Dynamic Theming**: Supports **Light**, **Dark**, and a **Custom (Orange/Amber)** theme.
- **Theme Switching**: Users can switch themes instantly via the Options Menu without restarting the app.
- **RecyclerView**: Efficiently displays large lists of users with a custom Adapter.
- **Menus**:
    - **Options Menu**: Global actions (Theme, Logout).
    - **Popup Menu**: Quick actions (Edit, Delete simulation) on list items.
    - **Context Menu**: Supported for long-press interactions.

### 4. Advanced Components
- **WebView Integration**: Browses user websites directly within the app.
- **Smart Loading**: Includes loading indicators and error handling for web content.
- **Navigation**: Uses explicit Intents for seamless screen transitions.
- **SSL/TLS**: Handles secure and cleartext traffic appropriately.

## Tech Stack

- **Language**: Java
- **Minimum SDK**: 24 (Android 7.0)
- **Architecture**: MVC / MVVM-Light
- **Libraries**:
    - [Retrofit](https://square.github.io/retrofit/) (Networking)
    - [Gson](https://github.com/google/gson) (JSON Parsing)
    - [Material Components](https://github.com/material-components/material-components-android) (UI Design)
    - [ConstraintLayout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout) (Responsive UI)

## Setup & Installation

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/yourusername/MyApplication5.git
    ```
2.  **Open in Android Studio**:
    - Launch Android Studio.
    - Select "Open an Existing Project".
    - Navigate to the cloned directory.
3.  **Sync Gradle**: Allow Android Studio to download dependencies.
4.  **Run the App**:
    - Connect a device or start an emulator.
    - Click the **Run** (Play) button.

## Usage Guide

1.  **Login**: Enter any username and password to log in.
2.  **View Data**: The main screen loads users from the API.
3.  **Themes**: Tap the 3-dot menu (top-right) -> Select a Theme.
4.  **Web Profile**: Tap any user card to view their website.
5.  **Actions**: Long-press a user card for more options.
6.  **Logout**: Tap the 3-dot menu -> Logout.

## License

This project is open-source and available under the simple [MIT License](LICENSE).
