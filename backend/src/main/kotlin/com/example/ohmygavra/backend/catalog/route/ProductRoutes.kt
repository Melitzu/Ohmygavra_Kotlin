package com.example.ohmygavra.backend.catalog.route

import com.example.ohmygavra.backend.catalog.service.ProductNotFoundException
import com.example.ohmygavra.backend.catalog.service.ProductService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.productRoutes(productService: ProductService) {
    route("/products") {
        get {
            val products = productService.getProducts()
            call.respond(HttpStatusCode.OK, products)
        }

        get("/{id}") {
            val productId = call.parameters["id"]?.toIntOrNull()

            if (productId == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Id invalido."))
                return@get
            }

            try {
                val product = productService.getProductById(productId)
                call.respond(HttpStatusCode.OK, product)
            } catch (exception: ProductNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to exception.message))
            }
        }
    }
}
