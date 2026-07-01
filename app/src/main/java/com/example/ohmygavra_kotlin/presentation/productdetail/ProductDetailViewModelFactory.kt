package com.example.ohmygavra_kotlin.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ohmygavra_kotlin.data.repository.FakeProductRepository
import com.example.ohmygavra_kotlin.domain.usecase.GetProductByIdUseCase

// ViewModel factory: inyecta el caso de uso que depende del contrato de repositorio.
class ProductDetailViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            val repository = FakeProductRepository()
            val getProductByIdUseCase = GetProductByIdUseCase(repository)
            return ProductDetailViewModel(getProductByIdUseCase) as T
        }

        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
