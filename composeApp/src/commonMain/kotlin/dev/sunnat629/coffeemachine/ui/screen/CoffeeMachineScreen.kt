package dev.sunnat629.coffeemachine.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sunnat629.coffeemachine.data.CupSize
import dev.sunnat629.coffeemachine.ui.components.*
import dev.sunnat629.coffeemachine.ui.theme.CoffeeMachineColors
import dev.sunnat629.coffeemachine.viewmodel.CoffeeMachineViewModel

@Composable
fun CoffeeMachineScreen(
    viewModel: CoffeeMachineViewModel = remember { CoffeeMachineViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    val sizeOptions = remember {
        listOf(
            CupSize("Small", "Small", 0.7f),
            CupSize("Medium", "Medium", 1.0f),
            CupSize("Large", "Large", 1.3f),
            CupSize("XLarge", "X-Large", 1.6f),
            CupSize("Custom", "Custom", 1.2f)
        )
    }

    val currentSize = remember(uiState.selectedSize) {
        sizeOptions.find { it.id == uiState.selectedSize } ?: sizeOptions[1]
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeMachineColors.ButtonBackground)
    ) {
        // Header
        CoffeeMachineHeader(
            title = "Caramel Frappuccino",
            onBackClick = { /* Handle back navigation */ },
            onDeleteClick = { /* Handle delete action */ }
        )

        // Coffee Machine
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = CoffeeMachineColors.SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                // Machine Body
                CoffeeMachineBody(
                    currentSize = currentSize,
                    isBrewingAnimationActive = uiState.isBrewingAnimationActive
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Price
                Text(
                    text = uiState.price,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = CoffeeMachineColors.TextPrimary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }

        // Size Options
        SizeSelector(
            sizeOptions = sizeOptions,
            selectedSize = uiState.selectedSize,
            onSizeSelected = viewModel::selectSize,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Quantity and Fill Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = CoffeeMachineColors.SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Quantity Selector
                QuantitySelector(
                    quantity = uiState.quantity,
                    onIncrease = viewModel::incrementQuantity,
                    onDecrease = viewModel::decrementQuantity,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Animated Fill Button
                AnimatedFillButton(
                    fillProgress = uiState.fillProgress,
                    fillState = uiState.fillState,
                    onFillClick = viewModel::startFilling
                )
            }
        }
    }
}
