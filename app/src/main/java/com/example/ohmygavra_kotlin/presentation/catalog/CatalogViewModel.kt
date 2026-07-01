package com.example.ohmygavra_kotlin.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ohmygavra_kotlin.domain.usecase.GetProductsUseCase

// ViewModel: prepara el estado de UI llamando al caso de uso, sin conocer la vista.
class CatalogViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<CatalogUiState>(CatalogUiState.Loading)
    val uiState: LiveData<CatalogUiState> = _uiState

    init {
        loadProducts()
    }

    fun loadProducts() {
        _uiState.value = CatalogUiState.Loading

        try {
            val products = getProductsUseCase()
            _uiState.value = if (products.isEmpty()) {
                CatalogUiState.Empty
            } else {
                CatalogUiState.Success(products)
            }
        } catch (exception: Exception) {
            _uiState.value = CatalogUiState.Error("No se pudo cargar el catalogo.")
        }
    }
}
