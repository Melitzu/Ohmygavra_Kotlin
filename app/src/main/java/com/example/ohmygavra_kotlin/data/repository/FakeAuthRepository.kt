package com.example.ohmygavra_kotlin.data.repository

import com.example.ohmygavra_kotlin.domain.model.User
import com.example.ohmygavra_kotlin.domain.repository.AuthRepository
import com.example.ohmygavra_kotlin.domain.usecase.LoginResult
import com.example.ohmygavra_kotlin.domain.usecase.RegisterResult

// Repository: simula una fuente de datos de auth hasta reemplazarla por backend real.
class FakeAuthRepository : AuthRepository {

    override fun login(email: String, password: String): LoginResult {
        val user = users.find { registeredUser ->
            registeredUser.email == email && registeredUser.password == password
        }

        return if (user != null) {
            LoginResult.Success(User(email = user.email, name = user.name))
        } else {
            LoginResult.Error("Credenciales incorrectas.")
        }
    }

    override fun register(name: String, email: String, password: String, age: Int): RegisterResult {
        val emailAlreadyExists = users.any { user -> user.email.equals(email, ignoreCase = true) }

        if (emailAlreadyExists) {
            return RegisterResult.Error("El correo ya esta registrado.")
        }

        users.add(RegisteredUser(name = name, email = email, password = password, age = age))
        return RegisterResult.Success(User(email = email, name = name))
    }

    private data class RegisteredUser(
        val name: String,
        val email: String,
        val password: String,
        val age: Int
    )

    private companion object {
        val users = mutableListOf(
            RegisteredUser(
                name = "Alumno Ohmygavra",
                email = "alumno@ohmygavra.cl",
                password = "123456",
                age = 20
            )
        )
    }
}
