package com.example.ohmygavra.backend.catalog.route

import com.example.ohmygavra.backend.catalog.dto.ProductRequest
import com.example.ohmygavra.backend.catalog.service.ProductNotFoundException
import com.example.ohmygavra.backend.catalog.service.ProductService
import com.example.ohmygavra.backend.catalog.service.ProductValidationException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
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

        post {
            try {
                val request = call.receive<ProductRequest>()
                val product = productService.createProduct(request)
                call.respond(HttpStatusCode.Created, product)
            } catch (exception: ProductValidationException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to exception.message))
            } catch (exception: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Body invalido."))
            }
        }

        put("/{id}") {
            val productId = call.parameters["id"]?.toIntOrNull()

            if (productId == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Id invalido."))
                return@put
            }

            try {
                val request = call.receive<ProductRequest>()
                val product = productService.updateProduct(productId, request)
                call.respond(HttpStatusCode.OK, product)
            } catch (exception: ProductValidationException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to exception.message))
            } catch (exception: ProductNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to exception.message))
            } catch (exception: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Body invalido."))
            }
        }

        delete("/{id}") {
            val productId = call.parameters["id"]?.toIntOrNull()

            if (productId == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Id invalido."))
                return@delete
            }

            try {
                productService.deleteProduct(productId)
                call.respond(HttpStatusCode.NoContent)
            } catch (exception: ProductNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to exception.message))
            }
        }
    }
}
