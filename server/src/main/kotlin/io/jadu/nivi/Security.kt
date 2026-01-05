package io.jadu.nivi

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.jadu.nivi.utils.auth_Jwt
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val config = environment.config

    val audience = config.property("jwt.audience").getString()
    val domain = config.property("jwt.domain").getString()
    val secret = config.property("jwt.secret").getString()
    val jwtRealm = config.property("jwt.realm").getString()

    install(Authentication) {
        jwt(auth_Jwt) {
            realm = jwtRealm
            verifier (
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(domain)
                    .build()
            )
            validate { credentials ->
                if (credentials.payload.audience.contains(audience)) {
                    JWTPrincipal(credentials.payload)
                } else {
                    null
                }
            }
        }
    }
}
