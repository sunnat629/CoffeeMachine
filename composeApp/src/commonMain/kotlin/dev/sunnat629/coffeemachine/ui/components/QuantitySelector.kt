package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.data.FillState
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@Composable
fun QuantitySelector(
    quantity: Int,
    fillState: FillState,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left button: Decrease (Idle/Filling) or Delete (Completed)
        when (fillState) {
            FillState.Completed -> {
                // Show Delete button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            Color.Red.copy(alpha = 0.1f),
                            CircleShape
                        )
                        .clickable { onDelete() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            else -> {
                // Show Decrease button
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "Decrease",
                    modifier = Modifier.clickable(enabled = fillState == FillState.Idle) { 
                        if (fillState == FillState.Idle) onDecrease() 
                    },
                    tint = if (fillState == FillState.Idle) 
                        CoffeeMachineColors.TextSecondary 
                    else 
                        CoffeeMachineColors.TextSecondary.copy(alpha = 0.4f)
                )
            }
        }

        Text(
            text = quantity.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = CoffeeMachineColors.TextPrimary
        )

        // Right button: Increase (only visible in Idle/Filling states)
        if (fillState != FillState.Completed) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        if (fillState == FillState.Idle)
                            CoffeeMachineColors.MachineBackground
                        else
                            CoffeeMachineColors.MachineBackground.copy(alpha = 0.4f),
                        CircleShape
                    )
                    .clickable(enabled = fillState == FillState.Idle) { 
                        if (fillState == FillState.Idle) onIncrease() 
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = if (fillState == FillState.Idle) 
                        CoffeeMachineColors.TextSecondary 
                    else 
                        CoffeeMachineColors.TextSecondary.copy(alpha = 0.4f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}