plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    application
}

application {
    mainClass.set("com.example.ohmygavra.backend.ApplicationKt")
}

tasks.register<JavaExec>("setupDatabase") {
    group = "application"
    description = "Creates Neon/PostgreSQL tables and seeds catalog demo data."
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.example.ohmygavra.backend.config.DatabaseSetupKt")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.hikari)

    implementation(libs.jbcrypt)
    implementation(libs.logback.classic)

    testImplementation(kotlin("test"))
}
