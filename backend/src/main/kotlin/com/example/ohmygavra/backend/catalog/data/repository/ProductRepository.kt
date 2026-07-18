package com.example.ohmygavra.backend.catalog.data.repository

import com.example.ohmygavra.backend.catalog.data.table.ProductsTable
import com.example.ohmygavra.backend.catalog.dto.ProductRequest
import com.example.ohmygavra.backend.catalog.dto.ProductResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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

    fun createProduct(request: ProductRequest): ProductResponse = transaction {
        val productId = ProductsTable.insert { product ->
            product[nombre] = request.nombre
            product[precio] = request.precio
            product[descripcion] = request.descripcion
            product[imagen] = request.imagen
            product[stock] = request.stock
        } get ProductsTable.id

        ProductResponse(
            id = productId.value,
            nombre = request.nombre,
            precio = request.precio,
            descripcion = request.descripcion,
            imagen = request.imagen,
            stock = request.stock
        )
    }

    fun updateProduct(productId: Int, request: ProductRequest): ProductResponse? = transaction {
        val updatedRows = ProductsTable.update({ ProductsTable.id eq productId }) { product ->
            product[nombre] = request.nombre
            product[precio] = request.precio
            product[descripcion] = request.descripcion
            product[imagen] = request.imagen
            product[stock] = request.stock
        }

        if (updatedRows == 0) {
            null
        } else {
            ProductResponse(
                id = productId,
                nombre = request.nombre,
                precio = request.precio,
                descripcion = request.descripcion,
                imagen = request.imagen,
                stock = request.stock
            )
        }
    }

    fun deleteProduct(productId: Int): Boolean = transaction {
        ProductsTable.deleteWhere { ProductsTable.id eq productId } > 0
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
