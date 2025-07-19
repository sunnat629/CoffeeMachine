package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@Composable
fun CupDesignSelector(
    selectedDesign: Int,
    onDesignChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CoffeeMachineColors.SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Cup Design",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoffeeMachineColors.TextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous design button
                IconButton(
                    onClick = {
                        val newDesign = (selectedDesign - 1 + 3) % 3
                        onDesignChange(newDesign)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Previous design",
                        tint = CoffeeMachineColors.TextSecondary
                    )
                }

                // Cup design preview
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(getCupDesignColor(selectedDesign))
                            .border(
                                width = 2.dp,
                                color = CoffeeMachineColors.TextSecondary,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Cup handle
                        Box(
                            modifier = Modifier
                                .offset(x = 40.dp)
                                .size(width = 20.dp, height = 40.dp)
                                .border(
                                    width = 2.dp,
                                    color = CoffeeMachineColors.TextSecondary,
                                    shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                                )
                        )
                        
                        // Cup pattern based on design
                        when (selectedDesign) {
                            0 -> {
                                // Classic design - horizontal lines
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    repeat(3) {
                                        HorizontalDivider(
                                            color = Color.White.copy(alpha = 0.7f),
                                            thickness = 2.dp,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                    }
                                }
                            }
                            1 -> {
                                // Modern design - dots pattern
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    repeat(3) { row ->
                                        Column(
                                            verticalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            repeat(3) { col ->
                                                Box(
                                                    modifier = Modifier
                                                        .size(6.dp)
                                                        .clip(CircleShape)
                                                        .background(Color.White.copy(alpha = 0.7f))
                                                )
                                                if (col < 2) Spacer(modifier = Modifier.height(8.dp))
                                            }
                                        }
                                        if (row < 2) Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }
                            2 -> {
                                // Fancy design - diagonal pattern
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    for (i in 0 until 5) {
                                        HorizontalDivider(
                                            color = Color.White.copy(alpha = 0.7f),
                                            thickness = 2.dp,
                                            modifier = Modifier
                                                .width(80.dp)
                                                .rotate(45f)
                                                .offset(
                                                    x = (-20 + i * 20).dp,
                                                    y = (-20 + i * 20).dp
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = when (selectedDesign) {
                            0 -> "Classic"
                            1 -> "Modern"
                            else -> "Fancy"
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = CoffeeMachineColors.TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }

                // Next design button
                IconButton(
                    onClick = {
                        val newDesign = (selectedDesign + 1) % 3
                        onDesignChange(newDesign)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Next design",
                        tint = CoffeeMachineColors.TextSecondary
                    )
                }
            }
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