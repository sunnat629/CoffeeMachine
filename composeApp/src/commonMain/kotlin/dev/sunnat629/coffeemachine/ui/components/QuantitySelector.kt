package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Default.Remove,
            contentDescription = "Decrease",
            modifier = Modifier.clickable { onDecrease() },
            tint = CoffeeMachineColors.TextSecondary
        )
        Text(
            text = quantity.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = CoffeeMachineColors.TextPrimary
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    CoffeeMachineColors.MachineBackground,
                    CircleShape
                )
                .clickable { onIncrease() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Increase",
                tint = CoffeeMachineColors.TextSecondary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}