package com.example.data.mapper.implement

import com.example.data.mapper.Mapper
import com.example.domain.model.UserDto
import com.example.domain.response.User

object UserBodyMapper : Mapper<UserDto, User> {

    override fun mapTo(input: UserDto): User {
        return User(
            userId = input.userId,
            fullName = input.fullName,
            photo = input.photo,
            email = input.email,
            phoneNumber = input.phoneNumber,
            createdAt = input.createdAt
        )
    }
}