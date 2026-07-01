package com.example.ohmygavra.backend

import com.example.ohmygavra.backend.auth.data.repository.UserRepository
import com.example.ohmygavra.backend.auth.route.authRoutes
import com.example.ohmygavra.backend.auth.service.UserService
import com.example.ohmygavra.backend.catalog.data.repository.ProductRepository
import com.example.ohmygavra.backend.catalog.route.productRoutes
import com.example.ohmygavra.backend.catalog.service.ProductService
import com.example.ohmygavra.backend.config.DatabaseFactory
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    embeddedServer(
        factory = Netty,
        host = "0.0.0.0",
        port = port,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    install(ContentNegotiation) {
        json()
    }

    val userRepository = UserRepository()
    val userService = UserService(userRepository)
    val productRepository = ProductRepository()
    val productService = ProductService(productRepository)

    routing {
        authRoutes(userService)
        productRoutes(productService)
    }
}
