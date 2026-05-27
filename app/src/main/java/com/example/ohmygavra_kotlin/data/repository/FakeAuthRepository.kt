package com.example.ohmygavra_kotlin.data.repository

import com.example.ohmygavra_kotlin.domain.model.User
import com.example.ohmygavra_kotlin.domain.repository.AuthRepository
import com.example.ohmygavra_kotlin.domain.usecase.LoginResult

class FakeAuthRepository : AuthRepository {

    override fun login(email: String, password: String): LoginResult {
        return if (email == VALID_EMAIL && password == VALID_PASSWORD) {
            LoginResult.Success(User(email = email, name = "Alumno Ohmygavra"))
        } else {
            LoginResult.Error("Credenciales incorrectas.")
        }
    }

    private companion object {
        const val VALID_EMAIL = "alumno@ohmygavra.cl"
        const val VALID_PASSWORD = "123456"
    }
}
