package com.example.ohmygavra_kotlin.domain.repository

import com.example.ohmygavra_kotlin.domain.usecase.LoginResult
import com.example.ohmygavra_kotlin.domain.usecase.RegisterResult

interface AuthRepository {
    fun login(email: String, password: String): LoginResult
    fun register(name: String, email: String, password: String, age: Int): RegisterResult
}
