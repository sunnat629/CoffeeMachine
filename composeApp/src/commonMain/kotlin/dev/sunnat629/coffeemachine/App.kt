package dev.sunnat629.coffeemachine

import androidx.compose.runtime.Composable
import dev.sunnat629.coffeemachine.ui.screen.CoffeeMachineScreen
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    CoffeeMachineTheme {
        CoffeeMachineScreen()
    }
}
