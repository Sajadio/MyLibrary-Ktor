package com.example.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser

fun Any.parseToJson(): JsonElement {
    return JsonParser.parseString(Gson().toJson(this))
}