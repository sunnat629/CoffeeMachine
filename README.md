# ☕ Coffee Machine App

A beautiful, interactive coffee machine interface built with **Jetpack Compose Multiplatform** featuring smooth animations, realistic brewing effects, and an intuitive user experience.

## 🎨 Design Inspiration

This project is inspired by the amazing design work of [Kingsley Orji](https://www.linkedin.com/posts/kingsleyorji_figma-justvibes-designforfun-activity-7346999024123609089-w2mK/). Thank you for creating such a beautiful and inspiring design! Your creativity sparked the development of this interactive implementation.

## 📱 Platform Support

This application is built using **Kotlin Multiplatform** and supports:

- **🤖 Android** (API 24+)
- **🍎 iOS** (iOS 18.2+)
- **🖥️ Desktop** (Windows, macOS, Linux)
- **🌐 Web** (via Kotlin/Wasm)

## ✨ Features

### 🎯 Core Functionality
- **Interactive Coffee Machine**: Realistic coffee machine interface with control panel
- **Dynamic Cup Sizing**: Smooth scaling animations for different cup sizes (Small, Medium, Large, X-Large)
- **Swipeable Cup Designs**: 3 unique cup patterns (Classic, Modern, Fancy) with swipe gestures
- **Quantity Management**: Intuitive quantity selector with smart state management
- **Shopping Cart**: Add items to cart with quantity tracking

### 🎬 Advanced Animations
- **Coffee Brewing Streams**: Realistic droplet animations with steam effects
- **Liquid Fill Animation**: Watery fill effects with waves, bubbles, and shimmer
- **Machine State Indicators**: Visual feedback when brewing is active
- **Pulsating Button Effects**: Engaging UI with glow and scale animations
- **Spring-based Transitions**: Natural motion using physics-based animations

### 🎨 Visual Design
- **Material Design 3**: Modern Material You design system
- **Custom Color Palette**: Coffee-themed color scheme
- **Glassmorphism Effects**: Modern glass-like visual elements
- **Responsive Layouts**: Adaptive design across all platforms
- **Accessibility Support**: Proper content descriptions and semantic markup

## 🏗️ Architecture & Best Practices

### 📐 Design Patterns
- **MVVM (Model-View-ViewModel)**: Clean separation of concerns
- **Unidirectional Data Flow**: Predictable state management
- **Repository Pattern**: Clean data layer abstraction
- **Dependency Injection Ready**: Structured for DI frameworks

### 🎛️ State Management
- **Jetpack Compose State**: Reactive UI updates with `StateFlow`
- **ViewModel Lifecycle**: Proper lifecycle-aware components
- **Immutable Data Classes**: Predictable state mutations
- **Coroutines**: Asynchronous operations and animations

### 🧩 Component Architecture
```
├── ui/
│   ├── components/          # Reusable UI components
│   │   ├── AnimatedCup.kt
│   │   ├── CoffeeBrewingStream.kt
│   │   ├── AnimatedFillButton.kt
│   │   └── SwipeableCup.kt
│   ├── screen/              # Screen-level composables
│   ├── theme/               # Design system
│   └── state/               # UI state definitions
├── viewmodel/               # Business logic
└── data/                    # Data models
```

## 🛠️ Technology Stack

### **Core Framework**
- **Kotlin Multiplatform** (2.2.0)
- **Jetpack Compose Multiplatform** (1.8.2)
- **Material Design 3**

### **Architecture Components**
- **Lifecycle ViewModel** (2.9.1)
- **Kotlin Coroutines** (1.10.2)
- **StateFlow** for reactive programming

### **Animation & Graphics**
- **Compose Animation APIs**
- **Custom Canvas Drawing**
- **Mathematical Functions** for realistic physics
- **Gesture Detection** for swipe interactions

### **Build System**
- **Gradle** (8.13) with Kotlin DSL
- **Android Gradle Plugin** (8.10.0)
- **Compose Hot Reload** for faster development

## 🚀 Getting Started

### Prerequisites
- **JDK 11** or higher
- **Android Studio** (latest stable)
- **Xcode** (for iOS development)
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/sunnat629/coffeemachine.git
   cd coffeemachine
   ```

2. **Build for Android**
   ```bash
   ./gradlew :composeApp:assembleDebug
   ```

3. **Run on Desktop**
   ```bash
   ./gradlew :composeApp:run
   ```

4. **Build for iOS**
   ```bash
   ./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
   ```

5. **Run Web Version**
   ```bash
   ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
   ```

## 📱 Screenshots

*The app features a modern coffee machine interface with smooth animations and intuitive controls across all platforms.*

## 🎯 Key Components

### AnimatedFillButton
```kotlin
@Composable
fun AnimatedFillButton(
    fillProgress: Float,
    fillState: FillState,
    onFillClick: () -> Unit,
    onAddToCart: () -> Unit
)
```
Features realistic liquid filling with waves, bubbles, and shimmer effects.

### SwipeableCup
```kotlin
@Composable
fun SwipeableCup(
    selectedDesign: Int,
    onDesignChange: (Int) -> Unit
)
```
Interactive cup with swipe gestures to change design patterns.

### CoffeeBrewingStream
```kotlin
@Composable
fun CoffeeBrewingStream(
    animationDelay: Long = 0L
)
```
Realistic coffee droplet animation with steam effects.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Setup
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 Sunnat629

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👨‍💻 Author

**Sunnat629**
- GitHub: [@sunnat629](https://github.com/sunnat629)
- LinkedIn: [Connect with me](https://linkedin.com/in/sunnat629)

## 🙏 Acknowledgments

- **[Kingsley Orji](https://www.linkedin.com/posts/kingsleyorji_figma-justvibes-designforfun-activity-7346999024123609089-w2mK/)** for the inspiring original design
- **JetBrains** for Kotlin Multiplatform and Compose Multiplatform
- **Google** for Material Design and Jetpack Compose
- **Open Source Community** for the amazing tools and libraries

---

### ⭐ Support This Project

If you found this project helpful or interesting, please consider:

- **⭐ Starring** this repository
- **👥 Following** [@sunnat629](https://github.com/sunnat629) for more projects
- **🐛 Reporting** any issues you find
- **💡 Suggesting** new features or improvements

Your support helps me create more open-source projects! Thank you! 🙏

---

*Built with ❤️ using Kotlin Multiplatform and Jetpack Compose*
