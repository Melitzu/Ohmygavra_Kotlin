package com.example.ohmygavra.backend.auth.data.repository

import com.example.ohmygavra.backend.auth.data.table.UsersTable
import com.example.ohmygavra.backend.auth.dto.UserResponse
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    fun emailExists(email: String): Boolean = transaction {
        UsersTable
            .selectAll()
            .where { UsersTable.email eq email }
            .limit(1)
            .any()
    }

    fun createUser(
        name: String,
        email: String,
        passwordHash: String,
        age: Int
    ): UserResponse = transaction {
        val userId = UsersTable.insert {
            it[UsersTable.name] = name
            it[UsersTable.email] = email
            it[UsersTable.passwordHash] = passwordHash
            it[UsersTable.age] = age
        } get UsersTable.id

        UserResponse(
            id = userId.value,
            name = name,
            email = email,
            age = age
        )
    }
}
