package io.jadu.nivi.route

sealed class AppRoutes(val path: String) {
    object Auth : AppRoutes("/auth"){ // www.abc.com/auth
        const val REGISTER = "/register"
        const val LOGIN = "/login"
    }

    object Transactions : AppRoutes("/transactions")
}