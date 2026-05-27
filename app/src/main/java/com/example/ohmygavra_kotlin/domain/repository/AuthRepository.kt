package com.example.ohmygavra_kotlin.domain.repository

import com.example.ohmygavra_kotlin.domain.usecase.LoginResult

interface AuthRepository {
    fun login(email: String, password: String): LoginResult
}
