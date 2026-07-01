package com.example.ohmygavra_kotlin.presentation.productdetail

import com.example.ohmygavra_kotlin.domain.model.Product

// Presentation state: representa loading, exito o error para el detalle.
sealed class ProductDetailUiState {
    data object Loading : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
    data class Error(val message: String) : ProductDetailUiState()
}
