package com.example.ohmygavra_kotlin.presentation.catalog

import com.example.ohmygavra_kotlin.domain.model.Product

// Presentation state: describe que debe mostrar la pantalla del catalogo.
sealed class CatalogUiState {
    data object Loading : CatalogUiState()
    data class Success(val products: List<Product>) : CatalogUiState()
    data object Empty : CatalogUiState()
    data class Error(val message: String) : CatalogUiState()
}
