package com.example.ohmygavra_kotlin.domain.usecase

import com.example.ohmygavra_kotlin.domain.model.Product
import com.example.ohmygavra_kotlin.domain.repository.ProductRepository

// UseCase: contiene la accion de obtener el catalogo desde el contrato del repositorio.
class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): List<Product> {
        return productRepository.getProducts()
    }
}
