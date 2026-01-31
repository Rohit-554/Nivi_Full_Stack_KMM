package io.jadu.nivi.routing

import io.jadu.nivi.data.db.local.TransactionRepository
import io.jadu.nivi.models.ApiResponse
import io.jadu.nivi.models.Necessity
import io.jadu.nivi.models.Transaction
import io.jadu.nivi.models.TransactionResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import smile.data.DataFrame
import smile.math.MathEx

fun Route.transactionalRoute(repository: TransactionRepository) {
    authenticate("auth-jwt") {
        route("/transactions") {

            // get the transactions
            // process the transactions
            // output the query
            get {
                // Extract User ID from the Token
                val principal = call.principal<JWTPrincipal>()
                val userId = principal!!.payload.getClaim("userId").asInt()

                val list = repository.getAllTransactions(userId)
                val insights = getInsights(list)

                val response = TransactionResponse(
                    transactions = list,
                    status = insights["overallStatus"] as? String ?: "UNKNOWN",
                    healthScore = insights["healthScore"] as? Int ?: 0,
                    averagePerTransaction = insights["averagePerTransaction"] as? Double ?: 0.0,
                    volatility = insights["volatility"] as? Double ?: 0.0
                )

                call.respond(ApiResponse.success(response))

                application.log.info("Insights: $insights")
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


fun getInsights(list: List<Transaction>) : Map<String, Any> {

    val df = DataFrame.of(Transaction::class.java, list)
    val amounts: DoubleArray = df.column("amount").toDoubleArray()

    val meanAmount = MathEx.mean(amounts)
    val stdDev = MathEx.stdev(amounts)

    val needsSum = list.filter { it.necessity == Necessity.NEED }.sumOf { it.amount }
    val totalSum = list.sumOf { it.amount }
    val healthRatio = if (totalSum > 0) needsSum / totalSum else 0.0


    val status = when {
        healthRatio < 0.4 -> "OVER: You are spending too much on non-essentials."
        stdDev > (meanAmount * 1.5) -> "UNSTABLE: Your spending is very unpredictable."
        healthRatio >= 0.6 && stdDev < meanAmount -> "SAFE: Your finances are very healthy."
        else -> "STABLE: You are in a normal spending range."
    }

    return mapOf(
        "overallStatus" to status,
        "healthScore" to (healthRatio * 100).toInt(), // 0 to 100 score
        "averagePerTransaction" to meanAmount,
        "volatility" to stdDev
    )

}