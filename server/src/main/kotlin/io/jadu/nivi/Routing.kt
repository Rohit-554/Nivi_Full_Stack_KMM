package io.jadu.nivi

import io.jadu.nivi.data.db.local.TransactionRepository
import io.jadu.nivi.data.db.local.UserRepository
import io.jadu.nivi.routing.authRoute
import io.jadu.nivi.routing.transactionalRoute
import io.jadu.nivi.utils.TokenManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    repository: UserRepository,
    tokenManager: TokenManager,
    transactionRepository: TransactionRepository
) {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        authRoute(repository, tokenManager)
        transactionalRoute(transactionRepository)

        get("/") {
            call.respondText("Hello guys kaise ho aplog!")
        }
    }
}
