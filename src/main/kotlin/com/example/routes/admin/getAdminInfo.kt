package com.example.routes.admin
import com.example.data.mapper.implement.AdminBodyMapper
import com.example.domain.model.AdminDto
import com.example.repository.admin.AdminRepository
import com.example.routes.adminId
import com.example.utils.*
import com.example.utils.Response
import com.example.utils.response.AdminResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAdminInfo(repository: AdminRepository) {
    get {
        when (val result = repository.getAdminById(call.adminId.toInt())) {
            is Response.SuccessResponse -> {
                val adminDto = result.data as AdminDto
                val mapper = AdminBodyMapper.mapTo(adminDto)
                call.respond(
                    result.statusCode, AdminResponse(
                        status = OK,
                        message = result.message,
                        admin = mapper
                    )
                )
            }

            is Response.ErrorResponse -> {
                call.respond(
                    result.statusCode, AdminResponse(
                        status = ERROR,
                        message = result.message,
                    )
                )
            }
        }
    }
}