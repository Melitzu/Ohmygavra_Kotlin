package com.example.ohmygavra_kotlin.domain.usecase

import com.example.ohmygavra_kotlin.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String): LoginResult {
        val cleanEmail = email.trim()

        if (cleanEmail.isBlank()) {
            return LoginResult.Error("Debes ingresar tu correo.")
        }

        if (!cleanEmail.matches(EMAIL_REGEX)) {
            return LoginResult.Error("Ingresa un correo valido.")
        }

        if (password.isBlank()) {
            return LoginResult.Error("Debes ingresar tu contrasena.")
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            return LoginResult.Error("La contrasena debe tener al menos 6 caracteres.")
        }

        return authRepository.login(cleanEmail, password)
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    }
}
