package com.example.plugins


import com.example.domain.response.ErrorResponse
import com.example.utils.ERROR
import com.fasterxml.jackson.core.JsonParseException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {

        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }

        exception<AuthenticationException> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden)
        }
        exception<UserNotFoundException> { call, _ ->
            call.respond(HttpStatusCode.NotFound)
        }
        exception<TokenCreationException> { call, _ ->
            call.respond(HttpStatusCode.InternalServerError)
        }
        exception<Exception> { call, cause ->
            call.respond(HttpStatusCode.BadRequest,cause.localizedMessage)
        }
        exception<JsonParseException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    status = ERROR,
                    message = cause.message.toString(),
                    code = HttpStatusCode.BadRequest.description,
                )
            )
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
class UserNotFoundException : RuntimeException()
class TokenCreationException : RuntimeException()
