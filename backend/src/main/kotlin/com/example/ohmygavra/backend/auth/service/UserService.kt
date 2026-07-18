package com.example.ohmygavra.backend.auth.service

import com.example.ohmygavra.backend.auth.data.repository.UserRepository
import com.example.ohmygavra.backend.auth.dto.LoginRequest
import com.example.ohmygavra.backend.auth.dto.RegisterRequest
import com.example.ohmygavra.backend.auth.dto.UserResponse
import org.mindrot.jbcrypt.BCrypt

class UserService(
    private val userRepository: UserRepository
) {

    fun register(request: RegisterRequest): UserResponse {
        val cleanEmail = request.email.trim()

        validateEmail(cleanEmail)
        validatePassword(request.password)

        if (userRepository.emailExists(cleanEmail)) {
            throw ConflictException("El email ya esta registrado.")
        }

        val passwordHash = BCrypt.hashpw(request.password, BCrypt.gensalt())

        return userRepository.createUser(
            name = request.name,
            email = cleanEmail,
            passwordHash = passwordHash,
            age = request.age
        )
    }

    fun login(request: LoginRequest): UserResponse {
        val cleanEmail = request.email.trim()

        validateEmail(cleanEmail)

        val storedUser = userRepository.findByEmail(cleanEmail)
            ?: throw InvalidCredentialsException("Credenciales invalidas.")

        if (!BCrypt.checkpw(request.password, storedUser.passwordHash)) {
            throw InvalidCredentialsException("Credenciales invalidas.")
        }

        return UserResponse(
            id = storedUser.id,
            name = storedUser.name,
            email = storedUser.email,
            age = storedUser.age
        )
    }

    private fun validateEmail(email: String) {
        if (!email.matches(EMAIL_REGEX)) {
            throw ValidationException("Formato de email invalido.")
        }
    }

    private fun validatePassword(password: String) {
        if (password.length < MIN_PASSWORD_LENGTH) {
            throw ValidationException("El password debe tener minimo 8 caracteres.")
        }
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 8
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    }
}
