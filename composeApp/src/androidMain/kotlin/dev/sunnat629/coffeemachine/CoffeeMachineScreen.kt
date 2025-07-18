@file:OptIn(ExperimentalAnimationApi::class)

package dev.sunnat629.coffeemachine

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sin

// Data class for size options
data class CupSize(
    val id: String,
    val label: String,
    val scale: Float
)

// Fill animation state
sealed class FillState {
    object Idle : FillState()
    object Filling : FillState()
    object Completed : FillState()
}

@Composable
fun CoffeeMachineScreen() {
    var selectedSize by remember { mutableStateOf("Medium") }
    var quantity by remember { mutableStateOf(1) }
    var fillState by remember { mutableStateOf<FillState>(FillState.Idle) }
    var fillProgress by remember { mutableFloatStateOf(0f) }
    var isBrewingAnimationActive by remember { mutableStateOf(false) }

    val sizeOptions = listOf(
        CupSize("Small", "Small", 1.0f),
        CupSize("Medium", "Medium", 1.3f),
        CupSize("Large", "Large", 1.6f),
        CupSize("XLarge", "X-Large", 2.0f),
        CupSize("Custom", "Custom", 3.0f)
    )

    val currentSize = sizeOptions.find { it.id == selectedSize } ?: sizeOptions[1]

    // Animated cup scale
    val cupScale by animateFloatAsState(
        targetValue = currentSize.scale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cup_scale"
    )

    // Fill animation
    val animatedFillProgress by animateFloatAsState(
        targetValue = fillProgress,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        ),
        label = "fill_progress"
    )

    // Handle fill button click
    fun handleFill() {
        if (fillState == FillState.Filling) return

        fillState = FillState.Filling
        fillProgress = 0f
        isBrewingAnimationActive = true

        // Animate fill progress
        val animationJob = kotlinx.coroutines.CoroutineScope(
            kotlinx.coroutines.Dispatchers.Main
        ).launch {
            val duration = 3000L
            val startTime = System.currentTimeMillis()

            while (fillProgress < 1f) {
                val elapsed = System.currentTimeMillis() - startTime
                val progress = (elapsed.toFloat() / duration).coerceAtMost(1f)

                // Apply ease-in-out curve
                val easeProgress = if (progress < 0.5f) {
                    2f * progress * progress
                } else {
                    1f - (-2f * progress + 2f).pow(3f) / 2f
                }

                fillProgress = easeProgress

                if (progress >= 1f) break
                delay(16) // ~60 FPS
            }

            fillState = FillState.Completed
            isBrewingAnimationActive = false
            delay(500)
            fillState = FillState.Idle
            fillProgress = 0f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { },
                    tint = Color.Gray
                )
                Text(
                    text = "Caramel Frappuccino",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable { },
                    tint = Color.Gray
                )
            }
        }

        // Coffee Machine
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Machine Body
                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(220.dp)
                        .background(
                            Color(0xFFE5E7EB),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Control Panel
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(
                                    Color(0xFF374151),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Central Dial
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            Color(0xFF4B5563),
                                            CircleShape
                                        )
                                        .border(
                                            2.dp,
                                            Color(0xFF6B7280),
                                            CircleShape
                                        )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Control Buttons
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    repeat(8) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .background(
                                                    Color(0xFF6B7280),
                                                    CircleShape
                                                )
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Coffee Spouts with Brewing Animation
                        Box(
                            modifier = Modifier.height(40.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                repeat(2) { index ->
                                    Box(
                                        modifier = Modifier.width(8.dp)
                                    ) {
                                        // Spout
                                        Box(
                                            modifier = Modifier
                                                .width(8.dp)
                                                .height(24.dp)
                                                .background(
                                                    Color(0xFF4B5563),
                                                    RoundedCornerShape(
                                                        bottomStart = 4.dp,
                                                        bottomEnd = 4.dp
                                                    )
                                                )
                                        )

                                        // Brewing Stream Animation
                                        if (isBrewingAnimationActive) {
                                            CoffeeBrewingStream(
                                                modifier = Modifier
                                                    .offset(x = 2.dp, y = 24.dp)
                                                    .width(4.dp)
                                                    .height(40.dp),
                                                animationDelay = index * 100L
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Animated Cup
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .scale(cupScale),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            AnimatedCup()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Drip Tray
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(8.dp)
                                .background(
                                    Color(0xFF374151),
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Price
                Text(
                    text = "$30.00",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }

        // Size Options
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Size Options",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    sizeOptions.forEach { size ->
                        SizeOption(
                            size = size,
                            isSelected = selectedSize == size.id,
                            onSelect = { selectedSize = size.id }
                        )
                    }
                }
            }
        }

        // Quantity and Fill Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Quantity Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.clickable {
                                if (quantity > 1) quantity--
                            },
                            tint = Color.Gray
                        )
                        Text(
                            text = quantity.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    Color(0xFFE5E7EB),
                                    CircleShape
                                )
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Animated Fill Button
                AnimatedFillButton(
                    fillProgress = animatedFillProgress,
                    fillState = fillState,
                    onFillClick = { handleFill() }
                )
            }
        }
    }
}

@Composable
fun CoffeeBrewingStream(
    modifier: Modifier = Modifier,
    animationDelay: Long = 0L
) {
    var animationTime by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        val startTime = System.currentTimeMillis() + animationDelay
        while (true) {
            animationTime = System.currentTimeMillis() - startTime
            delay(16) // ~60 FPS
        }
    }

    Canvas(modifier = modifier) {
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


@Composable
fun AnimatedCup() {
    Canvas(
        modifier = Modifier.size(40.dp, 50.dp)
    ) {
        val cupPath = Path().apply {
            moveTo(size.width * 0.2f, size.height * 0.1f)
            lineTo(size.width * 0.8f, size.height * 0.1f)
            lineTo(size.width * 0.9f, size.height * 0.9f)
            lineTo(size.width * 0.1f, size.height * 0.9f)
            close()
        }

        // Cup body
        drawPath(
            path = cupPath,
            color = Color.White,
            style = Stroke(width = 2.dp.toPx())
        )

        // Coffee layers
        drawRect(
            color = Color(0xFFF59E0B),
            topLeft = Offset(size.width * 0.15f, size.height * 0.7f),
            size = Size(size.width * 0.7f, size.height * 0.2f)
        )

        drawRect(
            color = Color(0xFFEAB308),
            topLeft = Offset(size.width * 0.15f, size.height * 0.4f),
            size = Size(size.width * 0.7f, size.height * 0.3f)
        )

        drawRect(
            color = Color.White,
            topLeft = Offset(size.width * 0.15f, size.height * 0.1f),
            size = Size(size.width * 0.7f, size.height * 0.3f)
        )

        // Handle
        drawArc(
            color = Color.Gray,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.85f, size.height * 0.3f),
            size = Size(size.width * 0.3f, size.height * 0.4f),
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Composable
fun SizeOption(
    size: CupSize,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "size_option_scale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .size(60.dp)
            .background(
                color = if (isSelected) Color(0xFF10B981) else Color(0xFFF3F4F6),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Delete, // Using as cup icon placeholder
                contentDescription = size.label,
                tint = if (isSelected) Color.White else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = size.label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AnimatedFillButton(
    fillProgress: Float,
    fillState: FillState,
    onFillClick: () -> Unit
) {
    var waveTime by remember { mutableLongStateOf(0L) }

    // Wave animation for watery effect
    LaunchedEffect(fillState) {
        if (fillState == FillState.Filling) {
            val startTime = System.currentTimeMillis()
            while (fillState == FillState.Filling) {
                waveTime = System.currentTimeMillis() - startTime
                delay(16) // ~60 FPS
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = fillState == FillState.Idle) { onFillClick() }
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background
            drawRoundRect(
                color = Color(0xFFE5E7EB),
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
                        val waveX =
                            fillWidth + sin((y * waveFrequency + timeOffset).toDouble()).toFloat() * waveAmplitude
                        lineTo(waveX, y.toFloat())
                    }

                    lineTo(0f, size.height)
                    close()
                }

                drawPath(
                    path = wavePath,
                    color = Color(0xFF92400E)
                )

                // Add flowing liquid effect with gradient
                val liquidGradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF92400E),
                        Color(0xFFB45309),
                        Color(0xFF92400E)
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
                        color = Color(0xFF7C2D12).copy(alpha = 0.6f)
                    )
                }

                // Add bubbles for liquid effect
                if (fillState == FillState.Filling) {
                    repeat(5) { i ->
                        val bubbleX = (fillWidth * 0.3f) + (i * fillWidth * 0.15f)
                        val bubbleY =
                            size.height * 0.3f + sin((timeOffset + i * 0.5f).toDouble()).toFloat() * 10.dp.toPx()
                        val bubbleRadius = (2 + i % 3).dp.toPx()
                        val bubbleAlpha = 0.3f + sin((timeOffset * 2f + i).toDouble()).toFloat() * 0.2f

                        drawCircle(
                            color = Color.White.copy(alpha = bubbleAlpha.coerceIn(0.1f, 0.5f)),
                            radius = bubbleRadius,
                            center = Offset(bubbleX, bubbleY)
                        )
                    }
                }
            }
        }

        // Button Text with watery shimmer effect
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
                    Color.White.copy(alpha = shimmerAlpha)
                } else {
                    Color.White
                }

                Text(
                    text = when (state) {
                        FillState.Idle -> "Tap to fill"
                        FillState.Filling -> "Filling your cup"
                        FillState.Completed -> "Tap to fill"
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}

// Usage in your Activity/Fragment
@Composable
fun CoffeeMachineApp() {
    MaterialTheme {
        CoffeeMachineScreen()
    }
}