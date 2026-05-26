package com.johny.mediaverse.core.domain.utils

sealed class NetworkError(val code: Int? = null) : Error {
    data object RequestTimeout : NetworkError(408)
    data object TooManyRequest : NetworkError(429)
    data object NoInternet : NetworkError()
    data class ServerError(val statusCode: Int) : NetworkError(statusCode)
    data object SerializationError : NetworkError()
    data class Unknown(val statusCode: Int? = null) : NetworkError(statusCode)
}