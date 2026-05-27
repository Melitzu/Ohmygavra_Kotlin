package com.example.ohmygavra_kotlin.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ohmygavra_kotlin.domain.usecase.LoginResult
import com.example.ohmygavra_kotlin.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(LoginUiState())
    val uiState: LiveData<LoginUiState> = _uiState

    fun onEmailChanged(email: String) {
        val currentState = _uiState.value ?: LoginUiState()
        _uiState.value = currentState.copy(
            email = email,
            emailError = null,
            generalError = null,
            successMessage = null,
            isLoginEnabled = email.isNotBlank() && currentState.password.isNotBlank()
        )
    }

    fun onPasswordChanged(password: String) {
        val currentState = _uiState.value ?: LoginUiState()
        _uiState.value = currentState.copy(
            password = password,
            passwordError = null,
            generalError = null,
            successMessage = null,
            isLoginEnabled = currentState.email.isNotBlank() && password.isNotBlank()
        )
    }

    fun onLoginClicked() {
        val currentState = _uiState.value ?: LoginUiState()

        when (val result = loginUseCase(currentState.email, currentState.password)) {
            is LoginResult.Success -> {
                _uiState.value = currentState.copy(
                    emailError = null,
                    passwordError = null,
                    generalError = null,
                    successMessage = "Bienvenida/o, ${result.user.name}."
                )
            }

            is LoginResult.Error -> {
                _uiState.value = currentState.copy(
                    successMessage = null,
                    generalError = result.message,
                    emailError = fieldErrorForEmail(result.message),
                    passwordError = fieldErrorForPassword(result.message)
                )
            }
        }
    }

    private fun fieldErrorForEmail(message: String): String? {
        return if (message.contains("correo", ignoreCase = true)) message else null
    }

    private fun fieldErrorForPassword(message: String): String? {
        return if (message.contains("contrasena", ignoreCase = true)) message else null
    }
}
