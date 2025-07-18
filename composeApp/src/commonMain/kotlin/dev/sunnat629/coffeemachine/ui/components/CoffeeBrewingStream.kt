package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.sin

@Composable
fun CoffeeBrewingStream(
    modifier: Modifier = Modifier,
    animationDelay: Long = 0L
) {
    var animationTime by remember { mutableLongStateOf(0L) }
    
    LaunchedEffect(Unit) {
        var elapsedTime = 0L
        val startDelay = animationDelay
        
        // Wait for the initial delay
        if (startDelay > 0) {
            delay(startDelay)
        }
        
        // Start animation timer
        val frameTime = 16L // ~60 FPS
        while (true) {
            elapsedTime += frameTime
            animationTime = elapsedTime
            delay(frameTime)
        }
    }
    
    Canvas(modifier = modifier) {
        if (animationTime <= 0) return@Canvas
        
        val timeSeconds = animationTime / 1000f
        val streamWidth = 4.dp.toPx()
        val dropletCount = 8
        
        // Draw continuous stream with animated droplets
        for (i in 0 until dropletCount) {
            val dropletOffset = (timeSeconds * 200f + i * 30f) % (size.height + 60f)
            val alpha = when {
                dropletOffset > size.height -> 0f
                dropletOffset < 0f -> 0f
                else -> (1f - dropletOffset / size.height) * 0.8f
            }
            
            if (alpha > 0f) {
                // Main stream droplet
                drawCircle(
                    color = Color(0xFF8B4513).copy(alpha = alpha),
                    radius = streamWidth / 2f,
                    center = Offset(streamWidth / 2f, dropletOffset)
                )
                
                // Smaller trailing droplets for realism
                if (i % 2 == 0) {
                    drawCircle(
                        color = Color(0xFFA0522D).copy(alpha = alpha * 0.6f),
                        radius = streamWidth / 3f,
                        center = Offset(streamWidth / 2f, dropletOffset + 10f)
                    )
                }
            }
        }
        
        // Add steam effect
        for (i in 0 until 3) {
            val steamOffset = (timeSeconds * 50f + i * 40f) % (size.height + 100f)
            val steamAlpha = when {
                steamOffset > size.height -> 0f
                steamOffset < 0f -> 0f
                else -> (1f - steamOffset / size.height) * 0.3f
            }
            
            if (steamAlpha > 0f) {
                drawCircle(
                    color = Color.White.copy(alpha = steamAlpha),
                    radius = (2 + i).dp.toPx(),
                    center = Offset(
                        streamWidth / 2f + sin(steamOffset / 20f) * 3.dp.toPx(),
                        steamOffset
                    )
                )
            }
        }
    }
}