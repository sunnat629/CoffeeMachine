package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@Composable
fun AnimatedCup(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.size(40.dp, 50.dp)
    ) {
        val cupPath = Path().apply {
            moveTo(size.width * 0.2f, size.height * 0.1f)
            lineTo(size.width * 0.8f, size.height * 0.1f)
            lineTo(size.width * 0.9f, size.height * 0.9f)
            lineTo(size.width * 0.1f, size.height * 0.9f)
            close()
        }

        // Cup body outline
        drawPath(
            path = cupPath,
            color = CoffeeMachineColors.SurfaceWhite,
            style = Stroke(width = 2.dp.toPx())
        )

        // Coffee layers
        drawRect(
            color = CoffeeMachineColors.CoffeeLight,
            topLeft = Offset(size.width * 0.15f, size.height * 0.7f),
            size = Size(size.width * 0.7f, size.height * 0.2f)
        )
        
        drawRect(
            color = CoffeeMachineColors.CoffeeMedium,
            topLeft = Offset(size.width * 0.15f, size.height * 0.4f),
            size = Size(size.width * 0.7f, size.height * 0.3f)
        )
        
        drawRect(
            color = CoffeeMachineColors.SurfaceWhite,
            topLeft = Offset(size.width * 0.15f, size.height * 0.1f),
            size = Size(size.width * 0.7f, size.height * 0.3f)
        )

        // Cup handle
        drawArc(
            color = CoffeeMachineColors.TextSecondary,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.85f, size.height * 0.3f),
            size = Size(size.width * 0.3f, size.height * 0.4f),
            style = Stroke(width = 2.dp.toPx())
        )
    }
}