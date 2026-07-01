package com.example.ohmygavra_kotlin.domain.usecase

import com.example.ohmygavra_kotlin.domain.repository.AuthRepository

// UseCase: valida las reglas de registro antes de delegar en el repositorio.
class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(name: String, email: String, password: String, ageText: String): RegisterResult {
        val cleanName = name.trim()
        val cleanEmail = email.trim()
        val age = ageText.toIntOrNull()

        if (cleanName.isBlank()) {
            return RegisterResult.Error("Debes ingresar tu nombre.")
        }

        if (cleanEmail.isBlank()) {
            return RegisterResult.Error("Debes ingresar tu correo.")
        }

        if (!cleanEmail.matches(EMAIL_REGEX)) {
            return RegisterResult.Error("Ingresa un correo valido.")
        }

        if (password.isBlank()) {
            return RegisterResult.Error("Debes ingresar tu contrasena.")
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            return RegisterResult.Error("La contrasena debe tener al menos 8 caracteres.")
        }

        if (age == null || age <= 0) {
            return RegisterResult.Error("Debes ingresar una edad valida.")
        }

        return authRepository.register(cleanName, cleanEmail, password, age)
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 8
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    }
}
