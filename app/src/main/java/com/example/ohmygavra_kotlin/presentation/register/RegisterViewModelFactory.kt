package com.example.ohmygavra_kotlin.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ohmygavra_kotlin.data.repository.FakeAuthRepository
import com.example.ohmygavra_kotlin.domain.usecase.RegisterUseCase

// ViewModel factory: crea dependencias concretas solo en el borde de presentation.
class RegisterViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            val repository = FakeAuthRepository()
            val registerUseCase = RegisterUseCase(repository)
            return RegisterViewModel(registerUseCase) as T
        }

        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
