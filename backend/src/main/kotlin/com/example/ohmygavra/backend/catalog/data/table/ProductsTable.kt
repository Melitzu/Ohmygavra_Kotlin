package com.example.ohmygavra.backend.catalog.data.table

import org.jetbrains.exposed.dao.id.IntIdTable

object ProductsTable : IntIdTable("products") {
    val nombre = varchar("nombre", 120)
    val precio = integer("precio")
    val descripcion = text("descripcion")
    val imagen = varchar("imagen", 120)
    val stock = integer("stock")
}
