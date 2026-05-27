package com.example.ohmygavra_kotlin.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null,
    val successMessage: String? = null,
    val isLoginEnabled: Boolean = false
)
