package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.data.FillState
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors
import kotlinx.coroutines.delay
import kotlin.math.sin

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedFillButton(
    fillProgress: Float,
    fillState: FillState,
    onFillClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    var waveTime by remember { mutableLongStateOf(0L) }

    // Pulsating animation for idle state to make button more prominent
    val pulsateAnimation = rememberInfiniteTransition(label = "pulsate_animation")
    val scale by pulsateAnimation.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "button_scale"
    )

    // Glow animation for idle state
    val glowAlpha by pulsateAnimation.animateFloat(
        initialValue = 0.0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    // Wave animation for watery effect
    LaunchedEffect(fillState) {
        if (fillState == FillState.Filling) {
            var elapsedTime = 0L
            val frameTime = 16L // ~60 FPS
            while (fillState == FillState.Filling) {
                elapsedTime += frameTime
                waveTime = elapsedTime
                delay(frameTime)
            }
        }
    }

    // Apply scale animation only in idle state
    val buttonScale = if (fillState == FillState.Idle) scale else 1.0f

    // Determine if button should be enabled and what action to perform
    val isEnabled = fillState != FillState.Filling
    val buttonAction = when (fillState) {
        FillState.Idle -> onFillClick
        FillState.Completed -> onAddToCart
        FillState.Filling -> { {} } // No action during filling
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp) // Slightly taller for more prominence
            .shadow(
                elevation = if (fillState == FillState.Idle || fillState == FillState.Completed) 8.dp else 4.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = CoffeeMachineColors.SelectedGreen
            )
            .scale(buttonScale) // Apply pulsating scale
            .clip(RoundedCornerShape(24.dp)) // More rounded corners
            .clickable(enabled = isEnabled) {
                if (isEnabled) buttonAction()
            }
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background - changes color based on fill state
            val backgroundColor = when (fillState) {
                FillState.Filling -> CoffeeMachineColors.MachineBackground
                FillState.Idle, FillState.Completed -> CoffeeMachineColors.SelectedGreen
            }

            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = CornerRadius(16.dp.toPx())
            )

            // Fill animation with watery effect
            val fillWidth = size.width * fillProgress
            if (fillWidth > 0) {
                val waveAmplitude = 4.dp.toPx()
                val waveFrequency = 0.01f
                val timeOffset = waveTime * 0.005f

                // Create watery wave path
                val wavePath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(fillWidth, 0f)

                    // Create wave effect on the right edge
                    for (y in 0..size.height.toInt() step 2) {
                        val waveX = fillWidth + sin((y * waveFrequency + timeOffset).toDouble()).toFloat() * waveAmplitude
                        lineTo(waveX, y.toFloat())
                    }

                    lineTo(0f, size.height)
                    close()
                }

                drawPath(
                    path = wavePath,
                    color = CoffeeMachineColors.CoffeeDark
                )

                // Add flowing liquid effect with gradient
                val liquidGradient = Brush.horizontalGradient(
                    colors = listOf(
                        CoffeeMachineColors.CoffeeDark,
                        CoffeeMachineColors.CoffeeLight,
                        CoffeeMachineColors.CoffeeDark
                    ),
                    startX = fillWidth - 100.dp.toPx(),
                    endX = fillWidth
                )

                clipRect(right = fillWidth) {
                    drawRoundRect(
                        brush = liquidGradient,
                        size = size,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }

                // Enhanced drip effect with watery motion
                if (fillProgress > 0.2f) {
                    val dripPath = Path().apply {
                        val waveOffset = sin((timeOffset * 2f).toDouble()).toFloat() * 2.dp.toPx()
                        moveTo(fillWidth + waveOffset, 0f)
                        lineTo(fillWidth - 20.dp.toPx(), 0f)
                        lineTo(fillWidth - waveOffset, size.height)
                        lineTo(fillWidth - 10.dp.toPx(), size.height)
                        close()
                    }
                    drawPath(
                        path = dripPath,
                        color = CoffeeMachineColors.CoffeeDarker.copy(alpha = 0.6f)
                    )
                }

                // Add bubbles for liquid effect
                if (fillState == FillState.Filling) {
                    repeat(5) { i ->
                        val bubbleX = (fillWidth * 0.3f) + (i * fillWidth * 0.15f)
                        val bubbleY = size.height * 0.3f + sin((timeOffset + i * 0.5f).toDouble()).toFloat() * 10.dp.toPx()
                        val bubbleRadius = (2 + i % 3).dp.toPx()
                        val bubbleAlpha = 0.3f + sin((timeOffset * 2f + i).toDouble()).toFloat() * 0.2f

                        drawCircle(
                            color = CoffeeMachineColors.SurfaceWhite.copy(alpha = bubbleAlpha.coerceIn(0.1f, 0.5f)),
                            radius = bubbleRadius,
                            center = Offset(bubbleX, bubbleY)
                        )
                    }
                }
            }
        }

        // Add glow effect behind the button when idle or completed
        if (fillState == FillState.Idle || fillState == FillState.Completed) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawCircle(
                    color = CoffeeMachineColors.SelectedGreen.copy(alpha = glowAlpha),
                    radius = size.width * 0.4f,
                    center = Offset(size.width / 2, size.height / 2)
                )
            }
        }

        // Button Text with enhanced effects
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = fillState,
                label = "button_text"
            ) { state ->
                val textColor = if (state == FillState.Filling) {
                    // Add shimmer effect during filling
                    val shimmerAlpha = 0.7f + 0.3f * sin((waveTime * 0.01f).toDouble()).toFloat()
                    CoffeeMachineColors.SurfaceWhite.copy(alpha = shimmerAlpha)
                } else {
                    CoffeeMachineColors.SurfaceWhite
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (state) {
                            FillState.Idle -> "TAP TO FILL"
                            FillState.Filling -> "FILLING YOUR CUP"
                            FillState.Completed -> "ADD TO CART"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor
                    )

                    if (state == FillState.Idle) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Swipe cup to change design",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = CoffeeMachineColors.SurfaceWhite.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}