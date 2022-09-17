package com.example.utils.validate

import com.example.utils.Response
import com.example.utils.SUCCESS
import io.ktor.http.*

class ValidatePassword {

    fun execute(password: String): Response<Any> {
        if (password.length <= 6) {
            return Response.ErrorResponse(
                message = "The password needs to consist of at least 8 characters",
                statusCode = HttpStatusCode.BadRequest
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return Response.ErrorResponse(
                message = "The password needs to contain at least one letter and digit",
                statusCode = HttpStatusCode.BadRequest
            )
        }
        return Response.SuccessResponse(message = SUCCESS, statusCode = HttpStatusCode.OK)
    }
}