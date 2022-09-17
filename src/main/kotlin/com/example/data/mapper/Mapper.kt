package com.example.data.mapper

import org.jetbrains.exposed.sql.ResultRow

interface Mapper<I,O> {
    fun mapTo(input:I):O
}