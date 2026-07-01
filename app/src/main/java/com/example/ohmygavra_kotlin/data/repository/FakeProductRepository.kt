package com.example.ohmygavra_kotlin.data.repository

import com.example.ohmygavra_kotlin.R
import com.example.ohmygavra_kotlin.domain.model.Product
import com.example.ohmygavra_kotlin.domain.repository.ProductRepository

// Repository: fuente de datos temporal que simula el catalogo hasta conectar Ktor/PostgreSQL.
class FakeProductRepository : ProductRepository {

    private val products = listOf(
        Product(
            id = 1,
            nombre = "Anillo Aurora",
            precio = 38990,
            descripcion = "Anillo dorado con piedra central brillante, pensado para looks elegantes y uso diario.",
            imagenResId = R.drawable.jewel_ring,
            stock = 8
        ),
        Product(
            id = 2,
            nombre = "Collar Luna",
            precio = 45990,
            descripcion = "Collar fino con dije lunar, acabado pulido y cadena delicada ajustable.",
            imagenResId = R.drawable.jewel_necklace,
            stock = 10
        ),
        Product(
            id = 3,
            nombre = "Aros Estrella",
            precio = 29990,
            descripcion = "Aros livianos con forma de estrella, ideales para combinar con outfits casuales o de noche.",
            imagenResId = R.drawable.jewel_earrings,
            stock = 14
        ),
        Product(
            id = 4,
            nombre = "Pulsera Serena",
            precio = 34990,
            descripcion = "Pulsera de eslabones finos con cierre seguro, comoda para uso diario.",
            imagenResId = R.drawable.jewel_bracelet,
            stock = 7
        ),
        Product(
            id = 5,
            nombre = "Set Perla Clara",
            precio = 64990,
            descripcion = "Set de collar y aros con perlas sinteticas, acabado clasico y presentacion elegante.",
            imagenResId = R.drawable.jewel_pearl_set,
            stock = 5
        ),
        Product(
            id = 6,
            nombre = "Broche Flor",
            precio = 22990,
            descripcion = "Broche decorativo con forma de flor, detalles dorados y centro brillante.",
            imagenResId = R.drawable.jewel_brooch,
            stock = 11
        )
    )

    override fun getProducts(): List<Product> {
        return products
    }

    override fun getProductById(productId: Int): Product? {
        return products.find { product -> product.id == productId }
    }
}
