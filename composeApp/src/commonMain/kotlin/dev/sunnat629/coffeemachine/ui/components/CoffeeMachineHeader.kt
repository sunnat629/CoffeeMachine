package dev.sunnat629.coffeemachine.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeMachineHeader(
    title: String,
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
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
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBackClick() },
                tint = CoffeeMachineColors.TextSecondary
            )
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoffeeMachineColors.TextPrimary
            )
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier.clickable { onDeleteClick() },
                tint = CoffeeMachineColors.TextSecondary
            )
        }
    }
}