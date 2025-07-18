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

    fun selectSize(sizeId: String) {
        _uiState.value = _uiState.value.copy(selectedSize = sizeId)
    }

    fun incrementQuantity() {
        _uiState.value = _uiState.value.copy(
            quantity = _uiState.value.quantity + 1
        )
    }

    fun decrementQuantity() {
        if (_uiState.value.quantity > 1) {
            _uiState.value = _uiState.value.copy(
                quantity = _uiState.value.quantity - 1
            )
        }
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

            delay(500)

            _uiState.value = _uiState.value.copy(
                fillState = FillState.Idle,
                fillProgress = 0f
            )
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
