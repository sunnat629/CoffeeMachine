package dev.sunnat629.coffeemachine

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.sunnat629.coffeemachine.ui.screen.CoffeeMachineScreen
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    CoffeeMachineTheme {
        Scaffold(
//            contentWindowInsets = WindowInsets(0, 0, 0, 0), // Handle system insets properly
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                CoffeeMachineScreen()
            }
        }
    }
}
