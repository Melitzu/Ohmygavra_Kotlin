package com.example.ohmygavra.backend.catalog.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val imagen: String,
    val stock: Int
)
