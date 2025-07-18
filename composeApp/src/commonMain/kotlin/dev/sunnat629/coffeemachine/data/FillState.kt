package dev.sunnat629.coffeemachine.data

sealed class FillState {
    object Idle : FillState()
    object Filling : FillState()
    object Completed : FillState()
}