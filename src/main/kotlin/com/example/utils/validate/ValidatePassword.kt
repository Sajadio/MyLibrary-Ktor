package com.example.utils.validate

import com.example.domain.response.AuthResponse
import com.example.utils.*
import io.ktor.http.*

object ValidatePassword {

    fun execute(password: String): AuthResponse {
        if (password.length <= 6) {
            return AuthResponse(
                status = ERROR,
                message = PASSWORD_SHORT,
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return AuthResponse(
                status = ERROR,
                message = PASSWORD_LETTER,
            )
        }
        return AuthResponse(status = OK, message = SUCCESS)
    }
}