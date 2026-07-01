package com.example.ohmygavra_kotlin.presentation.register

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val age: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val ageError: String? = null,
    val generalError: String? = null,
    val successMessage: String? = null,
    val isRegisterEnabled: Boolean = false
)
