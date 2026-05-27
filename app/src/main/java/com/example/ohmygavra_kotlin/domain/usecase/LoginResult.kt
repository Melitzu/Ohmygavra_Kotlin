package com.example.ohmygavra_kotlin.domain.usecase

import com.example.ohmygavra_kotlin.domain.model.User

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
