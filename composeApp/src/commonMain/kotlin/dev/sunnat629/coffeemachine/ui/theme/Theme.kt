package dev.sunnat629.coffeemachine.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CoffeeMachineColors.SelectedGreen,
    secondary = CoffeeMachineColors.CoffeeLight,
    tertiary = CoffeeMachineColors.CoffeeDark,
    background = CoffeeMachineColors.ButtonBackground,
    surface = CoffeeMachineColors.SurfaceWhite,
    onPrimary = CoffeeMachineColors.SurfaceWhite,
    onSecondary = CoffeeMachineColors.TextPrimary,
    onTertiary = CoffeeMachineColors.SurfaceWhite,
    onBackground = CoffeeMachineColors.TextPrimary,
    onSurface = CoffeeMachineColors.TextPrimary,
)

private val LightColorScheme = lightColorScheme(
    primary = CoffeeMachineColors.SelectedGreen,
    secondary = CoffeeMachineColors.CoffeeLight,
    tertiary = CoffeeMachineColors.CoffeeDark,
    background = CoffeeMachineColors.ButtonBackground,
    surface = CoffeeMachineColors.SurfaceWhite,
    onPrimary = CoffeeMachineColors.SurfaceWhite,
    onSecondary = CoffeeMachineColors.TextPrimary,
    onTertiary = CoffeeMachineColors.SurfaceWhite,
    onBackground = CoffeeMachineColors.TextPrimary,
    onSurface = CoffeeMachineColors.TextPrimary,
)

@Composable
fun CoffeeMachineTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}