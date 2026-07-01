package com.example.ohmygavra_kotlin.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ohmygavra_kotlin.domain.usecase.RegisterResult
import com.example.ohmygavra_kotlin.domain.usecase.RegisterUseCase

// ViewModel: recibe eventos de la pantalla y expone el estado del formulario.
class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(RegisterUiState())
    val uiState: LiveData<RegisterUiState> = _uiState

    fun onNameChanged(name: String) {
        val currentState = _uiState.value ?: RegisterUiState()
        _uiState.value = currentState.copy(
            name = name,
            nameError = null,
            generalError = null,
            successMessage = null,
            isRegisterEnabled = isFormFilled(name, currentState.email, currentState.password, currentState.age)
        )
    }

    fun onEmailChanged(email: String) {
        val currentState = _uiState.value ?: RegisterUiState()
        _uiState.value = currentState.copy(
            email = email,
            emailError = null,
            generalError = null,
            successMessage = null,
            isRegisterEnabled = isFormFilled(currentState.name, email, currentState.password, currentState.age)
        )
    }

    fun onPasswordChanged(password: String) {
        val currentState = _uiState.value ?: RegisterUiState()
        _uiState.value = currentState.copy(
            password = password,
            passwordError = null,
            generalError = null,
            successMessage = null,
            isRegisterEnabled = isFormFilled(currentState.name, currentState.email, password, currentState.age)
        )
    }

    fun onAgeChanged(age: String) {
        val currentState = _uiState.value ?: RegisterUiState()
        _uiState.value = currentState.copy(
            age = age,
            ageError = null,
            generalError = null,
            successMessage = null,
            isRegisterEnabled = isFormFilled(currentState.name, currentState.email, currentState.password, age)
        )
    }

    fun onRegisterClicked() {
        val currentState = _uiState.value ?: RegisterUiState()

        when (
            val result = registerUseCase(
                currentState.name,
                currentState.email,
                currentState.password,
                currentState.age
            )
        ) {
            is RegisterResult.Success -> {
                _uiState.value = currentState.copy(
                    nameError = null,
                    emailError = null,
                    passwordError = null,
                    ageError = null,
                    generalError = null,
                    successMessage = "Cuenta creada para ${result.user.name}."
                )
            }

            is RegisterResult.Error -> {
                _uiState.value = currentState.copy(
                    successMessage = null,
                    generalError = result.message,
                    nameError = fieldErrorForName(result.message),
                    emailError = fieldErrorForEmail(result.message),
                    passwordError = fieldErrorForPassword(result.message),
                    ageError = fieldErrorForAge(result.message)
                )
            }
        }
    }

    private fun isFormFilled(name: String, email: String, password: String, age: String): Boolean {
        return name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && age.isNotBlank()
    }

    private fun fieldErrorForName(message: String): String? {
        return if (message.contains("nombre", ignoreCase = true)) message else null
    }

    private fun fieldErrorForEmail(message: String): String? {
        return if (message.contains("correo", ignoreCase = true)) message else null
    }

    private fun fieldErrorForPassword(message: String): String? {
        return if (message.contains("contrasena", ignoreCase = true)) message else null
    }

    private fun fieldErrorForAge(message: String): String? {
        return if (message.contains("edad", ignoreCase = true)) message else null
    }
}
