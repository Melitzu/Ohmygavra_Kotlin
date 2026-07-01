package com.example.ohmygavra_kotlin.domain.repository

import com.example.ohmygavra_kotlin.domain.model.Product

// Repository: contrato que permite reemplazar datos fake por backend sin tocar la UI.
interface ProductRepository {
    fun getProducts(): List<Product>
    fun getProductById(productId: Int): Product?
}
