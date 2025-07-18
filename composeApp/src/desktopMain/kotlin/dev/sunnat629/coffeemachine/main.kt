package dev.sunnat629.coffeemachine

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CoffeeMachine",
    ) {
        App()
    }
}