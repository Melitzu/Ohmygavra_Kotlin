package com.example.ohmygavra.backend.catalog.service

import com.example.ohmygavra.backend.catalog.data.repository.ProductRepository
import com.example.ohmygavra.backend.catalog.dto.ProductRequest
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

    fun createProduct(request: ProductRequest): ProductResponse {
        val cleanRequest = request.clean()
        validateProduct(cleanRequest)

        return productRepository.createProduct(cleanRequest)
    }

    fun updateProduct(productId: Int, request: ProductRequest): ProductResponse {
        val cleanRequest = request.clean()
        validateProduct(cleanRequest)

        return productRepository.updateProduct(productId, cleanRequest)
            ?: throw ProductNotFoundException("Producto no encontrado.")
    }

    fun deleteProduct(productId: Int) {
        val wasDeleted = productRepository.deleteProduct(productId)

        if (!wasDeleted) {
            throw ProductNotFoundException("Producto no encontrado.")
        }
    }

    private fun ProductRequest.clean(): ProductRequest {
        return copy(
            nombre = nombre.trim(),
            descripcion = descripcion.trim(),
            imagen = imagen.trim()
        )
    }

    private fun validateProduct(request: ProductRequest) {
        when {
            request.nombre.isBlank() -> throw ProductValidationException("El nombre es obligatorio.")
            request.precio <= 0 -> throw ProductValidationException("El precio debe ser mayor a 0.")
            request.descripcion.isBlank() -> throw ProductValidationException("La descripcion es obligatoria.")
            request.imagen.isBlank() -> throw ProductValidationException("La imagen es obligatoria.")
            request.stock < 0 -> throw ProductValidationException("El stock no puede ser negativo.")
        }
    }
}

class ProductNotFoundException(message: String) : RuntimeException(message)

class ProductValidationException(message: String) : RuntimeException(message)
