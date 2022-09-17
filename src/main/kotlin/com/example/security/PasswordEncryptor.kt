package com.example.security

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private const val SECRET_KEY = "4968110512"
private const val ALGORITHM = "HmacSHA1"

private val HASH_KEY = hex(SECRET_KEY)
private val MAC_KEY = SecretKeySpec(HASH_KEY, ALGORITHM)

fun hashPWS(password: String): String{
    val mac = Mac.getInstance(ALGORITHM)
    mac.init(MAC_KEY)
    return hex(mac.doFinal(password.toByteArray(Charsets.UTF_8)))
}