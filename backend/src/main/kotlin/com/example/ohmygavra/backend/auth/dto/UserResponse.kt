package com.example.ohmygavra.backend.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val age: Int
)
