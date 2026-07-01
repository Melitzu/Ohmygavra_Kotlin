package com.example.ohmygavra.backend.catalog.service

import com.example.ohmygavra.backend.catalog.data.repository.ProductRepository
import com.example.ohmygavra.backend.catalog.dto.ProductResponse

class ProductService(
    private val productRepository: ProductRepository
) {

    fun getProducts(): List<ProductResponse> {
        return productRepository.getProducts()
    }

    fun getProductById(productId: Int): ProductResponse {
        return productRepository.getProductById(productId)
            ?: throw ProductNotFoundException("Producto no encontrado.")
    }
}

class ProductNotFoundException(message: String) : RuntimeException(message)
