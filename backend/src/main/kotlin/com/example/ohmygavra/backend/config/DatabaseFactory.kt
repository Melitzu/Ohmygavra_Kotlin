package com.example.ohmygavra.backend.config

import com.example.ohmygavra.backend.auth.data.table.UsersTable
import com.example.ohmygavra.backend.catalog.data.table.ProductsTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(createDataSource())

        transaction {
            SchemaUtils.create(UsersTable, ProductsTable)
        }
    }

    private fun createDataSource(): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl = getRequiredEnv("DB_URL")
            username = getRequiredEnv("DB_USER")
            password = getRequiredEnv("DB_PASSWORD")
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        return HikariDataSource(config)
    }

    private fun getRequiredEnv(name: String): String {
        return System.getenv(name)
            ?: error("La variable de entorno $name es obligatoria.")
    }
}
