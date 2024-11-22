# Coroutines Playground App

This repository contains an Android app built as a **playground for Kotlin Coroutines**, with examples ranging from fundamental concepts to advanced use cases. The app uses **Jetpack Compose** for UI and implements **Model-View-Intent (MVI)** as the architecture pattern.

---

## Features

### 1. Jetpack Compose with MVI
- The app demonstrates **reactive UI patterns** using Jetpack Compose with MVI architecture.
- ViewModel-driven state management ensures a clean separation of concerns.

---

### 2. Meditations

#### **1. Coroutines Scope Management**
- Examples of creating and managing coroutine scopes for different contexts (e.g., ViewModel, lifecycle-aware components).
- Insights into lifecycle-awareness with `viewModelScope` and `rememberCoroutineScope`.

#### **2. Avoiding Memory Leaks with Compose**
- Demonstrates how to properly launch coroutines in Compose without leaking memory.
- Example: Use of `LaunchedEffect`, `rememberCoroutineScope`, and cancellation-aware coroutines.

#### **3. Manual Job Cancellations for Fire-and-Forget Jobs**
- A dedicated example to showcase canceling jobs that do not need to propagate back to the UI.

#### **4. Transaction List Screen**
- A practical implementation of **Model-View-Intent (MVI)** architecture.
- Demonstrates a reactive UI for displaying transaction data using Jetpack Compose and handling user intents like filtering or refreshing.

#### **5. Structured Concurrency**

##### i. `await` and `awaitAll`
- Explains how to concurrently fetch data (e.g., APIs or databases) while waiting for all tasks to complete safely.

##### ii. `runCatching` and `getOrElse`
- Case studies showcasing robust error handling for asynchronous operations.

##### iii. `SupervisorJob`: Loan Eligibility Checker Flow
- A case-study-based example where independent subtasks in a **Loan Eligibility Checker flow** run under a `SupervisorJob`.
- Demonstrates resilience when one of the subtasks fails while others continue execution.

#### **6. Operators in Coroutines**

##### i. `combine`: E-KYC Synchronization
- Real-world example demonstrating the use of the `combine` operator to merge local and remote (API) data for an **E-KYC synchronization process**.
- The flow processes:
    - Identifying mismatched or outdated records.
    - Updating local or remote data as needed.
    - Ensuring consistency across data sources.

---

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: Model-View-Intent (MVI)
- **Concurrency**: Kotlin Coroutines and Flow

---

## Usage

This app is a **learning tool** for Kotlin Coroutines. Each screen and example is designed to be independent, allowing you to explore different topics without prerequisites.

1. Browse through the different **Meditations** in the app to study and practice coroutine concepts.
2. Use the **Transaction List Screen** to learn about **MVI architecture** and reactive state management with Jetpack Compose.
3. Modify or extend examples to suit your learning needs.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## Contributions

Contributions are welcome! If you find an issue or have an idea for an improvement, please submit a pull request or open an issue.

---

Happy learning! 🎉  
