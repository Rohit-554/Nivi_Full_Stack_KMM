package io.jadu.nivi.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.jadu.nivi.models.User
import io.ktor.server.config.ApplicationConfig
import java.util.Date
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant


class TokenManager(private val config: ApplicationConfig){
    @OptIn(ExperimentalTime::class)
    fun generateToken(user: User): String {
        val audience = config.property("jwt.audience").getString()
        val domain = config.property("jwt.domain").getString()
        val secret = config.property("jwt.secret").getString()

        val expirationTime = Clock.System.now().plus(7.days)
        val javaExpirationDate = Date.from(expirationTime.toJavaInstant())

        return JWT.create()
            .withAudience(audience)
            .withIssuer(domain)
            .withClaim("userId", user.id)
            .withClaim("email", user.email)
            .withExpiresAt(javaExpirationDate)
            .sign(Algorithm.HMAC256(secret))
    }
}