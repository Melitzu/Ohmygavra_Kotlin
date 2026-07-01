package com.example.ohmygavra.backend.catalog.data.repository

import com.example.ohmygavra.backend.catalog.data.table.ProductsTable
import com.example.ohmygavra.backend.catalog.dto.ProductResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepository {

    fun getProducts(): List<ProductResponse> = transaction {
        ProductsTable
            .selectAll()
            .orderBy(ProductsTable.id)
            .map { row -> row.toProductResponse() }
    }

    fun getProductById(productId: Int): ProductResponse? = transaction {
        ProductsTable
            .selectAll()
            .where { ProductsTable.id eq productId }
            .limit(1)
            .map { row -> row.toProductResponse() }
            .singleOrNull()
    }

    private fun ResultRow.toProductResponse(): ProductResponse {
        return ProductResponse(
            id = this[ProductsTable.id].value,
            nombre = this[ProductsTable.nombre],
            precio = this[ProductsTable.precio],
            descripcion = this[ProductsTable.descripcion],
            imagen = this[ProductsTable.imagen],
            stock = this[ProductsTable.stock]
        )
    }
}
