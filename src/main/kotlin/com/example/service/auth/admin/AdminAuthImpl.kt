package com.example.service.auth.admin

import com.example.database.DatabaseFactory
import com.example.database.table.AdminTable
import com.example.database.table.toAdminDto
import com.example.domain.model.AdminCredentials
import com.example.domain.model.NewAdmin
import com.example.security.hashPWS
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class AdminAuthImpl : AdminAuth {
    override suspend fun adminSignUp(newAdmin: NewAdmin) = DatabaseFactory.dbQuery {
        AdminTable.insert {
            it[fullName] = newAdmin.fullName
            it[urlPhoto] = ""
            it[email] = newAdmin.email
            it[password] = hashPWS(newAdmin.password)
            it[phoneNumber] = ""
        }.resultedValues?.map { result ->
            result.toAdminDto()
        }?.singleOrNull()
    }

    override suspend fun adminLogIn(adminCredentials: AdminCredentials) =
        DatabaseFactory.dbQuery {
            AdminTable.select {
                AdminTable.email eq
                        adminCredentials.email and
                        (AdminTable.password eq hashPWS(adminCredentials.password))
            }.firstOrNull()
        }.toAdminDto()


    override suspend fun findAdminByEmail(email: String) = DatabaseFactory.dbQuery {
        AdminTable.select { AdminTable.email.eq(email) }.map { result ->
            result.toAdminDto()
        }.singleOrNull()
    }
}