package com.example.ohmygavra.backend.config

import com.example.ohmygavra.backend.auth.data.table.UsersTable
import com.example.ohmygavra.backend.catalog.data.table.ProductsTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

fun main() {
    DatabaseFactory.init()
    seedDemoUser()
    seedProducts()
    println("Database setup completed.")
}

private fun seedDemoUser() {
    transaction {
        SchemaUtils.create(UsersTable)

        if (UsersTable.selectAll().empty()) {
            UsersTable.insert { user ->
                user[name] = "Alumno Ohmygavra"
                user[email] = "alumno@ohmygavra.cl"
                user[passwordHash] = BCrypt.hashpw("12345678", BCrypt.gensalt())
                user[age] = 20
            }
        }
    }
}

private fun seedProducts() {
    transaction {
        SchemaUtils.create(UsersTable, ProductsTable)

        if (ProductsTable.selectAll().empty()) {
            ProductsTable.batchInsert(jewelryProducts) { product ->
                this[ProductsTable.nombre] = product.nombre
                this[ProductsTable.precio] = product.precio
                this[ProductsTable.descripcion] = product.descripcion
                this[ProductsTable.imagen] = product.imagen
                this[ProductsTable.stock] = product.stock
            }
        }
    }
}

private data class JewelryProduct(
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val imagen: String,
    val stock: Int
)

private val jewelryProducts = listOf(
    JewelryProduct(
        nombre = "Anillo Aurora",
        precio = 38990,
        descripcion = "Anillo dorado con piedra central brillante, pensado para looks elegantes y uso diario.",
        imagen = "jewel_ring",
        stock = 8
    ),
    JewelryProduct(
        nombre = "Collar Luna",
        precio = 45990,
        descripcion = "Collar fino con dije lunar, acabado pulido y cadena delicada ajustable.",
        imagen = "jewel_necklace",
        stock = 10
    ),
    JewelryProduct(
        nombre = "Aros Estrella",
        precio = 29990,
        descripcion = "Aros livianos con forma de estrella, ideales para combinar con outfits casuales o de noche.",
        imagen = "jewel_earrings",
        stock = 14
    ),
    JewelryProduct(
        nombre = "Pulsera Serena",
        precio = 34990,
        descripcion = "Pulsera de eslabones finos con cierre seguro, comoda para uso diario.",
        imagen = "jewel_bracelet",
        stock = 7
    ),
    JewelryProduct(
        nombre = "Set Perla Clara",
        precio = 64990,
        descripcion = "Set de collar y aros con perlas sinteticas, acabado clasico y presentacion elegante.",
        imagen = "jewel_pearl_set",
        stock = 5
    ),
    JewelryProduct(
        nombre = "Broche Flor",
        precio = 22990,
        descripcion = "Broche decorativo con forma de flor, detalles dorados y centro brillante.",
        imagen = "jewel_brooch",
        stock = 11
    )
)
