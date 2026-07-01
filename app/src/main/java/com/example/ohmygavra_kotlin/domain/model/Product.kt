package com.example.ohmygavra_kotlin.domain.model

// Model: representa la entidad de negocio que usan las capas superiores.
data class Product(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val imagenResId: Int,
    val stock: Int
)
