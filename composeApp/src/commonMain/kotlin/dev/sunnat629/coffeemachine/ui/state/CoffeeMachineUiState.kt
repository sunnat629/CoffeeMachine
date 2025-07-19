package dev.sunnat629.coffeemachine.ui.state

import dev.sunnat629.coffeemachine.data.CupSize
import dev.sunnat629.coffeemachine.data.FillState

data class CoffeeMachineUiState(
    val selectedSize: String = "Medium",
    val quantity: Int = 1,
    val cartQuantity: Int = 0,
    val selectedCupDesign: Int = 0,
    val fillState: FillState = FillState.Idle,
    val fillProgress: Float = 0f,
    val isBrewingAnimationActive: Boolean = false,
    val price: String = "€3.00"
)