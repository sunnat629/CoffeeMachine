package dev.sunnat629.coffeemachine

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform