package com.example.ohmygavra.backend.auth.route

import com.example.ohmygavra.backend.auth.dto.LoginRequest
import com.example.ohmygavra.backend.auth.dto.RegisterRequest
import com.example.ohmygavra.backend.auth.service.ConflictException
import com.example.ohmygavra.backend.auth.service.InvalidCredentialsException
import com.example.ohmygavra.backend.auth.service.UserService
import com.example.ohmygavra.backend.auth.service.ValidationException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.authRoutes(userService: UserService) {
    route("/auth") {
        post("/login") {
            try {
                val request = call.receive<LoginRequest>()
                val user = userService.login(request)

                call.respond(HttpStatusCode.OK, user)
            } catch (exception: ValidationException) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to exception.message)
                )
            } catch (exception: InvalidCredentialsException) {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = mapOf("error" to exception.message)
                )
            } catch (exception: BadRequestException) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to "Body invalido.")
                )
            }
        }

        post("/register") {
            try {
                val request = call.receive<RegisterRequest>()
                val user = userService.register(request)

                call.respond(HttpStatusCode.Created, user)
            } catch (exception: ValidationException) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to exception.message)
                )
            } catch (exception: ConflictException) {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = mapOf("error" to exception.message)
                )
            } catch (exception: BadRequestException) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = mapOf("error" to "Body invalido.")
                )
            }
        }
    }
}
