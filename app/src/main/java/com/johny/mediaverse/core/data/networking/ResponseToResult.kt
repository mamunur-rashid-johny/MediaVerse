package com.johny.mediaverse.core.data.networking

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, NetworkError> {
    val code = response.status.value
    return when (code) {
        in 200..290 -> {
            try {
                Result.Success(response.body<T>())
            } catch (ex: NoTransformationFoundException) {
                Result.Error(NetworkError.SerializationError)
            }
        }
        408 -> Result.Error(NetworkError.RequestTimeout)
        429 -> Result.Error(NetworkError.TooManyRequest)
        in 500..599 -> Result.Error(NetworkError.ServerError(code))
        else -> Result.Error(NetworkError.Unknown(code))
    }
}