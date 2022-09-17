package com.example.utils.validate

import com.example.utils.Response
import com.example.utils.SUCCESS
import io.ktor.http.*
import java.util.regex.Pattern


class ValidateEmail {

    private val emailAddressPattern: Pattern =
        Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

    fun execute(email: String): Response<Any> {
        val matches = emailAddressPattern.matcher(email).matches()
        if (email.isEmpty()) {
            return Response.ErrorResponse(
                message = "The email can't be empty",
                statusCode = HttpStatusCode.BadRequest
            )
        }

        if (!matches) {
            return Response.ErrorResponse(
                message = "That's not a valid email",
                statusCode = HttpStatusCode.BadRequest
            )
        }
        return Response.SuccessResponse(message = SUCCESS, statusCode = HttpStatusCode.OK)
    }
}