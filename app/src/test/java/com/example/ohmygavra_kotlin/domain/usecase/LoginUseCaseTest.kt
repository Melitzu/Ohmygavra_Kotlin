package com.example.ohmygavra_kotlin.domain.usecase

import com.example.ohmygavra_kotlin.domain.model.User
import com.example.ohmygavra_kotlin.domain.repository.AuthRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUseCaseTest {

    @Test
    fun loginWithValidCredentialsReturnsSuccess() {
        val useCase = LoginUseCase(SuccessAuthRepository())

        val result = useCase("alumno@ohmygavra.cl", "123456")

        assertTrue(result is LoginResult.Success)
        assertEquals("Alumno Ohmygavra", (result as LoginResult.Success).user.name)
    }

    @Test
    fun loginWithInvalidEmailReturnsError() {
        val useCase = LoginUseCase(SuccessAuthRepository())

        val result = useCase("correo-invalido", "123456")

        assertTrue(result is LoginResult.Error)
        assertEquals("Ingresa un correo valido.", (result as LoginResult.Error).message)
    }

    private class SuccessAuthRepository : AuthRepository {
        override fun login(email: String, password: String): LoginResult {
            return LoginResult.Success(User(email = email, name = "Alumno Ohmygavra"))
        }

        override fun register(name: String, email: String, password: String, age: Int): RegisterResult {
            return RegisterResult.Success(User(email = email, name = name))
        }
    }
}
