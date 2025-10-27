# Market Baz

![Platform](https://img.shields.io/badge/Platform-Android-green)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-informational)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

**Market Baz** is a modern Android shopping application built with **Kotlin**, **Jetpack Compose**, and powered by **Clean Architecture**. It provides a seamless experience for both regular users and administrators. The app uses **Firebase** for product management and **Room Database** for local storage.

Users can browse products, add to favorites or cart, place orders, and leave ratings and comments. Administrators can manage products, users, orders, and discounts.

---

# Market Baz – Jetpack Compose Shopping App with Clean Architecture

## Features

### User Features
- 🛍️ Browse and purchase products
- ❤️ Add products to favorites
- 📝 Rate and comment on products
- 👤 Profile management: add profile picture, set address
- 🛒 Cart and order management
- 💸 Apply available discounts during checkout

### Admin Features
- 🔑 Admin access based on `idAdmin = true`
- ➕ Add, edit, and delete products
- 👥 Manage users and their orders
- 📊 View detailed sales reports
- 💰 Apply discounts to products
- ✏️ Edit or delete user comments and ratings

---

## Prerequisites
Before building and running the project, make sure you have the following installed:
- Android Studio (Hedgehog or newer)
- JDK 11+
- Gradle 8.14.3+
- Kotlin 2.2.0+
- Android Gradle Plugin (AGP) 8.11.1+
- Minimum SDK: 30
- Compile SDK: 36
- Target SDK: 36

---

## Used Technologies & Libraries
- Jetpack Compose (UI toolkit)
- Clean Architecture (Domain, Data, Presentation layers)
- Room (local database)
- Firebase (authentication & product data)
- Hilt (dependency injection)
- Coroutines (asynchronous programming)
- Retrofit (network operations)
- Navigation-Compose
- DataStore Preferences

---
## Getting Started

Clone the repository:
```bash
git clone https://github.com/AliAyali/market-baz.git
```

**Name:** Ali Ayali  
**GitHub:** [github.com/AliAyali](https://github.com/AliAyali)  

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.