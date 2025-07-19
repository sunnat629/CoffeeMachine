package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors
import kotlin.math.abs

@Composable
fun SwipeableCup(
    selectedDesign: Int,
    onDesignChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Detect swipe gestures
    var dragAmount by remember { mutableFloatStateOf(0f) }
    val swipeThreshold = 50f // Minimum distance to trigger a design change
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(40.dp, 50.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            // Change design based on swipe direction when drag ends
                            if (abs(dragAmount) > swipeThreshold) {
                                val direction = if (dragAmount > 0) -1 else 1
                                val newDesign = (selectedDesign + direction + 3) % 3
                                onDesignChange(newDesign)
                            }
                            dragAmount = 0f
                        },
                        onDragCancel = {
                            dragAmount = 0f
                        },
                        onHorizontalDrag = { _, dragDistance ->
                            dragAmount += dragDistance
                        }
                    )
                }
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

            // Cup body fill with design color
            drawPath(
                path = cupPath,
                color = getCupDesignColor(selectedDesign)
            )

            // Cup pattern based on design
            when (selectedDesign) {
                0 -> {
                    // Classic design - horizontal lines
                    val lineSpacing = size.height * 0.2f
                    for (i in 1..3) {
                        val y = size.height * 0.2f + i * lineSpacing / 3
                        drawLine(
                            color = Color.White.copy(alpha = 0.7f),
                            start = Offset(size.width * 0.25f, y),
                            end = Offset(size.width * 0.75f, y),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }
                1 -> {
                    // Modern design - dots pattern
                    val dotRadius = 2.dp.toPx()
                    val dotSpacingX = size.width * 0.25f
                    val dotSpacingY = size.height * 0.25f
                    
                    for (row in 0..2) {
                        for (col in 0..2) {
                            drawCircle(
                                color = Color.White.copy(alpha = 0.7f),
                                radius = dotRadius,
                                center = Offset(
                                    x = size.width * 0.25f + col * dotSpacingX,
                                    y = size.height * 0.25f + row * dotSpacingY
                                )
                            )
                        }
                    }
                }
                2 -> {
                    // Fancy design - diagonal pattern
                    val path = Path()
                    for (i in -2..2) {
                        path.reset()
                        path.moveTo(size.width * 0.2f, size.height * (0.3f + i * 0.15f))
                        path.lineTo(size.width * 0.8f, size.height * (0.7f + i * 0.15f))
                        
                        drawPath(
                            path = path,
                            color = Color.White.copy(alpha = 0.7f),
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }

            // Coffee layers
            drawRect(
                color = CoffeeMachineColors.CoffeeLight.copy(alpha = 0.7f),
                topLeft = Offset(size.width * 0.15f, size.height * 0.7f),
                size = Size(size.width * 0.7f, size.height * 0.2f)
            )
            
            drawRect(
                color = CoffeeMachineColors.CoffeeMedium.copy(alpha = 0.7f),
                topLeft = Offset(size.width * 0.15f, size.height * 0.4f),
                size = Size(size.width * 0.7f, size.height * 0.3f)
            )
            
            drawRect(
                color = CoffeeMachineColors.SurfaceWhite.copy(alpha = 0.7f),
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
}

// Helper function to get cup color based on design
private fun getCupDesignColor(designIndex: Int): Color {
    return when (designIndex) {
        0 -> CoffeeMachineColors.CoffeeMedium.copy(alpha = 0.3f)
        1 -> CoffeeMachineColors.CoffeeLight.copy(alpha = 0.5f)
        else -> Color(0xFFE0D0C0)
    }
}