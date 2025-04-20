# Quiz Game App

**Quiz Game** Android application where users can answer trivia questions.

## Application Overview

This app fetches questions from the [Open Trivia DB](https://opentdb.com/api_config.php), displays them in a **RecyclerView**. It uses clean architecture principles and modern Android development practices.

---

## Features

- Fetches quiz questions dynamically from a remote API
- Calculates and displays user's score
- Stores the last quiz score locally using Room
- Clean, and well-commented Kotlin codebase
- MVVM Architecture for better separation of concerns
- Unit Testing support for core data entities
- Dependency injection using Hilt & Dagger

---

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt & Dagger
- **Persistence**: Room
- **Testing**: Unit tests for `QuestionsEntity` and `ScoreEntity`
- **Build System**: Gradle (version 8.0)
- **Development Tool**: Android Studio Giraffe | 2022.3.1 Patch 4

---

## Testing

- Unit tests included for:
    - `QuestionsEntity`
    - `ScoreEntity`

---

### Steps
```bash
git clone https://github.com/punitgsolanki/quiz_app.git
cd quiz_app
./gradlew build
```

Open in Android Studio and run the app on your emulator/device.

---

## Project Structure

- [Source Code](https://github.com/punitgsolanki/quiz_app/tree/main/source)
- [APK Download](https://github.com/punitgsolanki/quiz_app/tree/main/assets/APK/QuizGame.apk)

---

## 📸 Screenshots

| Splash Screen | Quiz Screen | Result Screen |
|:-------------:|:-----------:|:-------------:|
| ![Splash](https://github.com/punitgsolanki/quiz_app/raw/main/assets/Splash.png) | ![Quiz](https://github.com/punitgsolanki/quiz_app/raw/main/assets/Quiz.png) | ![Result](https://github.com/punitgsolanki/quiz_app/raw/main/assets/Result.png) |

---

## 🔗 Links

- 🔗 [GitHub Repository](https://github.com/punitgsolanki/quiz_app.git)