package com.example.ohmygavra.backend.auth.service

class ValidationException(message: String) : RuntimeException(message)

class ConflictException(message: String) : RuntimeException(message)
