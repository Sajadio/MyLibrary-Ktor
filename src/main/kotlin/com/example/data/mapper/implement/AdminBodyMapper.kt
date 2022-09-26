package com.example.data.mapper.implement

import com.example.data.mapper.Mapper
import com.example.domain.model.AdminDto
import com.example.domain.model.UserDto
import com.example.utils.response.Admin
import com.example.utils.response.User

object AdminBodyMapper : Mapper<AdminDto, Admin> {
    override fun mapTo(input: AdminDto): Admin {
        return Admin(
            adminId = input.adminId,
            fullName = input.fullName,
            urlPhoto = input.urlPhoto,
            email = input.email,
            phoneNumber = input.phoneNumber,
            createdAt = input.createdAt
        )
    }
}