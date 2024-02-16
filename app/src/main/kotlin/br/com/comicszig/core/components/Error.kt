package br.com.comicszig.core.components

data class Error(
    val code: Int? = null,
    val message: String? = null,
    val errorType: ErrorType? = null
)

sealed class ErrorType
