package com.example.ohmygavra_kotlin.domain.usecase

import com.example.ohmygavra_kotlin.domain.model.Product
import com.example.ohmygavra_kotlin.domain.repository.ProductRepository

// UseCase: obtiene un producto especifico sin depender de la implementacion concreta.
class GetProductByIdUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: Int): Product? {
        return productRepository.getProductById(productId)
    }
}
