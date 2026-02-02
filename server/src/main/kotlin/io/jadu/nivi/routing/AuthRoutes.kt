package io.jadu.nivi.routing

import io.jadu.nivi.data.db.local.UserRepository
import io.jadu.nivi.models.ApiResponse
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.route.AppRoutes
import io.jadu.nivi.utils.TokenManager
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.mindrot.jbcrypt.BCrypt

fun Route.authRoute(userRepository: UserRepository, tokenManager: TokenManager) {
    route(AppRoutes.Auth.path) {
        post(AppRoutes.Auth.REGISTER) {
            try {
                val request = call.receive<RegisterRequest>()

                val existingUser = userRepository.findUserByEmail(request.email)
                existingUser?.let {
                    call.respond(HttpStatusCode.Conflict, ApiResponse.error<Nothing>("Email Already Exist"))
                    return@post
                }

                val newUser = userRepository.createUser(request)

                if(newUser==null) {
                    call.respond(HttpStatusCode.InternalServerError, ApiResponse.error<Nothing>("Failed to create user"))
                    return@post
                }

                val token = tokenManager.generateToken(newUser)

                call.respond(HttpStatusCode.Created, ApiResponse.success(
                    AuthResponse(
                        token,
                        newUser.name
                    )
                ))

            }catch(e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse.error<Nothing>("Invalid data: ${e.message}"))
            }
        }

        post(AppRoutes.Auth.LOGIN) {
            try {
                val request = call.receive<LoginRequest>()

                // 1. Find User
                val user = userRepository.findUserByEmail(request.email)
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized, ApiResponse.error<Nothing>("Invalid email or password"))
                    return@post
                }

                // 2. Check Password (The BCrypt Verification)
                val passwordMatch = BCrypt.checkpw(request.password, user.passwordHash)

                if (!passwordMatch) {
                    call.respond(HttpStatusCode.Unauthorized, ApiResponse.error<Nothing>("Invalid email or password"))
                    return@post
                }

                val token = tokenManager.generateToken(user)

                call.respond(HttpStatusCode.OK, ApiResponse.success(AuthResponse(token, user.name)))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse.error<Nothing>("Invalid request"))
            }
        }
    }
}


/*
*
* call.receive<T>() - to read and deserialize the request body
call.respond() - to send a response
call.request - to access request details (headers, parameters, etc.)
call.response - to manipulate the response
call.parameters - to access URL parameters
*
* */