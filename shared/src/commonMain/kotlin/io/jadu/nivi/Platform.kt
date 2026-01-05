package io.jadu.nivi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform