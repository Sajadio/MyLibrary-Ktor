package com.example.data.mapper.implement

import com.example.data.mapper.Mapper
import com.example.domain.model.UserDto
import com.example.domain.response.User

object UsersBodyMapper : Mapper<List<UserDto>, List<User>> {
    override fun mapTo(input: List<UserDto>): List<User> {
        val users = mutableListOf<User>()
        input.forEach {
            users.add(
                User(
                    userId = it.userId,
                    fullName = it.fullName,
                    urlPhoto = it.urlPhoto,
                    email = it.email,
                    phoneNumber = it.phoneNumber,
                    createdAt = it.createdAt
                )
            )
        }
        return users
    }
}