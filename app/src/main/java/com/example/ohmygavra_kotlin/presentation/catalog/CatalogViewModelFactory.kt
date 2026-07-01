package com.example.ohmygavra_kotlin.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ohmygavra_kotlin.data.repository.FakeProductRepository
import com.example.ohmygavra_kotlin.domain.usecase.GetProductsUseCase

// ViewModel factory: arma dependencias concretas en el borde de la app.
class CatalogViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            val repository = FakeProductRepository()
            val getProductsUseCase = GetProductsUseCase(repository)
            return CatalogViewModel(getProductsUseCase) as T
        }

        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
