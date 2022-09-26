package com.example.data.mapper


interface Mapper<I,O> {
    fun mapTo(input:I):O
}