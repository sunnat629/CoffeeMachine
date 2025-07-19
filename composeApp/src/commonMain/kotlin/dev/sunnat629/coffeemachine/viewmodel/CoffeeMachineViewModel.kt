package dev.sunnat629.coffeemachine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sunnat629.coffeemachine.data.FillState
import dev.sunnat629.coffeemachine.ui.state.CoffeeMachineUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.pow

class CoffeeMachineViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CoffeeMachineUiState())
    val uiState: StateFlow<CoffeeMachineUiState> = _uiState.asStateFlow()

    // Cup prices based on size
    private val cupPrices = mapOf(
        "Small" to 2.5,
        "Medium" to 3.0,
        "Large" to 3.5,
        "XLarge" to 4.0
    )

    private fun calculatePrice(size: String, quantity: Int): String {
        val basePrice = cupPrices[size] ?: 3.0
        val totalPrice = basePrice * quantity
        return "€${totalPrice.toInt()}.${((totalPrice * 100).toInt() % 100).toString().padStart(2, '0')}"
    }

    fun selectSize(sizeId: String) {
        _uiState.value = _uiState.value.copy(
            selectedSize = sizeId,
            price = calculatePrice(sizeId, _uiState.value.quantity)
        )
    }

    fun selectCupDesign(designIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedCupDesign = designIndex)
    }

    fun incrementQuantity() {
        // Only allow quantity changes in Idle state
        if (_uiState.value.fillState == FillState.Idle) {
            val newQuantity = _uiState.value.quantity + 1
            _uiState.value = _uiState.value.copy(
                quantity = newQuantity,
                price = calculatePrice(_uiState.value.selectedSize, newQuantity)
            )
        }
    }

    fun decrementQuantity() {
        // Only allow quantity changes in Idle state
        if (_uiState.value.fillState == FillState.Idle && _uiState.value.quantity > 1) {
            val newQuantity = _uiState.value.quantity - 1
            _uiState.value = _uiState.value.copy(
                quantity = newQuantity,
                price = calculatePrice(_uiState.value.selectedSize, newQuantity)
            )
        }
    }

    fun deleteOrder() {
        // Reset to initial state when delete is pressed
        _uiState.value = _uiState.value.copy(
            fillState = FillState.Idle,
            fillProgress = 0f,
            quantity = 1,
            price = calculatePrice(_uiState.value.selectedSize, 1)
        )
    }

    fun addToCart() {
        val currentCartQuantity = _uiState.value.cartQuantity
        val addedQuantity = _uiState.value.quantity
        _uiState.value = _uiState.value.copy(
            cartQuantity = currentCartQuantity + addedQuantity,
            // Reset to idle state after adding to cart
            fillState = FillState.Idle,
            fillProgress = 0f,
            quantity = 1,
            price = calculatePrice(_uiState.value.selectedSize, 1)
        )
    }

    fun startFilling() {
        if (_uiState.value.fillState == FillState.Filling) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                fillState = FillState.Filling,
                fillProgress = 0f,
                isBrewingAnimationActive = true
            )

            animateFillProgress()

            _uiState.value = _uiState.value.copy(
                fillState = FillState.Completed,
                isBrewingAnimationActive = false
            )

            // Don't automatically reset the state - user must press "ADD TO CART" or "Delete"
        }
    }

    private suspend fun animateFillProgress() {
        val duration = 3000L
        val steps = 180 // 3000ms / 16.6ms per frame ≈ 180 steps

        for (step in 0..steps) {
            val progress = (step.toFloat() / steps).coerceAtMost(1f)

            // Apply ease-in-out curve
            val easeProgress = if (progress < 0.5f) {
                2f * progress * progress
            } else {
                1f - (-2f * progress + 2f).pow(3f) / 2f
            }

            _uiState.value = _uiState.value.copy(fillProgress = easeProgress)

            if (progress >= 1f) break
            delay(16) // ~60 FPS
        }
    }
}