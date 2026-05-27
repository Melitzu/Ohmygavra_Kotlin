package com.example.ohmygavra_kotlin.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ohmygavra_kotlin.data.repository.FakeAuthRepository
import com.example.ohmygavra_kotlin.domain.usecase.LoginUseCase

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val repository = FakeAuthRepository()
            val loginUseCase = LoginUseCase(repository)
            return LoginViewModel(loginUseCase) as T
        }

        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
