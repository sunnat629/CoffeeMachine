package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeMachineHeader(
    title: String,
    cartQuantity: Int = 0,
    onCartClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CoffeeMachineColors.SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Empty space where back button was
            Spacer(modifier = Modifier.size(24.dp))
            
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoffeeMachineColors.TextPrimary
            )
            
            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    Icons.Default.LocalCafe,
                    contentDescription = "Cart",
                    modifier = Modifier.clickable { onCartClick() },
                    tint = CoffeeMachineColors.TextSecondary
                )
                
                if (cartQuantity > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(CoffeeMachineColors.SelectedGreen)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cartQuantity.toString(),
                            fontSize = 10.sp,
                            color = CoffeeMachineColors.SurfaceWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}