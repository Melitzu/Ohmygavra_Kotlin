package com.example.ohmygavra_kotlin.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ohmygavra_kotlin.domain.usecase.GetProductByIdUseCase

// ViewModel: obtiene el producto seleccionado y expone el estado para la pantalla.
class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: LiveData<ProductDetailUiState> = _uiState

    fun loadProduct(productId: Int) {
        _uiState.value = ProductDetailUiState.Loading

        val product = getProductByIdUseCase(productId)
        _uiState.value = if (product != null) {
            ProductDetailUiState.Success(product)
        } else {
            ProductDetailUiState.Error("Producto no encontrado.")
        }
    }
}
