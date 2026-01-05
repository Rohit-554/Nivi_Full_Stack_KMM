package io.jadu.nivi.routing

import io.jadu.nivi.data.db.local.TransactionRepository
import io.jadu.nivi.data.model.Transaction
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.transactionalRoute(repository: TransactionRepository) {
    authenticate("auth-jwt") {
        route("/transactions") {

            get {
                // Extract User ID from the Token
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()

                val list = repository.getAllTransactions(userId)
                call.respond(list)
            }

            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()

                // We force the userId from the token onto the transaction
                val t = call.receive<Transaction>().copy(userId = userId)

                val saved = repository.addTransaction(t)
                call.respond(HttpStatusCode.Created, saved)
            }
        }
    }
}