package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.data.CupSize
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@Composable
fun SizeSelector(
    sizeOptions: List<CupSize>,
    selectedSize: String,
    onSizeSelected: (String) -> Unit,
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
                text = "Size Options",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoffeeMachineColors.TextPrimary,
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
                        onSelect = { onSizeSelected(size.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SizeOption(
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
                color = if (isSelected) CoffeeMachineColors.SelectedGreen else CoffeeMachineColors.ButtonBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.LocalCafe,
                contentDescription = size.label,
                tint = if (isSelected) CoffeeMachineColors.SurfaceWhite else CoffeeMachineColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = size.label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) CoffeeMachineColors.SurfaceWhite else CoffeeMachineColors.TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}