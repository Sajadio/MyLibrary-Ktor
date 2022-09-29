package com.example.utils.validate

import com.example.domain.response.AuthResponse
import com.example.utils.*
import io.ktor.http.*
import java.util.regex.Pattern


object ValidateEmail {

    private val emailAddressPattern: Pattern =
        Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

    fun execute(email: String): AuthResponse {
        val matches = emailAddressPattern.matcher(email).matches()
        if (email.isEmpty()) {
            return AuthResponse(
                status = ERROR,
                message = EMAIL_EMPTY,
            )
        }

        if (!matches) {
            return AuthResponse(
                status = ERROR,
                message = EMAIL_VALID,
            )
        }
        return AuthResponse(status = OK, message = SUCCESS)
    }
}