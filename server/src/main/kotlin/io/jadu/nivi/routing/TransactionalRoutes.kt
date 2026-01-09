package io.jadu.nivi.routing

import io.jadu.nivi.data.db.local.TransactionRepository
import io.jadu.nivi.models.ApiResponse
import io.jadu.nivi.models.Transaction
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.transactionalRoute(repository: TransactionRepository) {
    authenticate("auth-jwt") {
        route("/transactions") {

            get {
                // Extract User ID from the Token
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()

                val list = repository.getAllTransactions(userId)
                call.respond(ApiResponse.success(list))
            }

            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()

                // We force the userId from the token onto the transaction
                val t = call.receive<Transaction>().copy(userId = userId)

                val saved = repository.addTransaction(t)
                call.respond(HttpStatusCode.Created, ApiResponse.success(saved))
            }
        }
    }
}