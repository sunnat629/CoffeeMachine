package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import dev.sunnat629.coffeemachine.data.CupSize
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@Composable
fun CoffeeMachineBody(
    currentSize: CupSize,
    isBrewingAnimationActive: Boolean,
    modifier: Modifier = Modifier
) {
    // Animated cup scale
    val cupScale by animateFloatAsState(
        targetValue = currentSize.scale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cup_scale"
    )
    
    Box(
        modifier = modifier
            .width(280.dp)
            .height(220.dp)
            .background(
                CoffeeMachineColors.MachineBackground,
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Control Panel
            CoffeeMachineControlPanel()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Coffee Spouts with Brewing Animation
            CoffeeSpotsWithBrewingAnimation(isBrewingAnimationActive)
            
            Spacer(modifier = Modifier.height(8.dp))
            
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
                        CoffeeMachineColors.MachineGray,
                        RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}

@Composable
private fun CoffeeMachineControlPanel() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                CoffeeMachineColors.MachineGray,
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
                        CoffeeMachineColors.MachineLightGray,
                        CircleShape
                    )
                    .border(
                        2.dp,
                        CoffeeMachineColors.MachineBackground,
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
                                CoffeeMachineColors.MachineBackground,
                                CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun CoffeeSpotsWithBrewingAnimation(isBrewingAnimationActive: Boolean) {
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
                                CoffeeMachineColors.MachineLightGray,
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
}