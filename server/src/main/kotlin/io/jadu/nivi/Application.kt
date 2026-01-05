package io.jadu.nivi

import com.sun.tools.javac.tree.TreeInfo.args
import io.jadu.nivi.data.db.local.TransactionRepository
import io.jadu.nivi.data.db.local.UserRepository
import io.jadu.nivi.utils.TokenManager
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val repository = TransactionRepository()
    val tokenManager = TokenManager(environment.config)
    val userRepository = UserRepository()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting(userRepository, tokenManager, repository)
}