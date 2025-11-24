package com.johny.mediaverse.core.data.networking

import com.johny.mediaverse.core.domain.utils.NetworkError
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import com.johny.mediaverse.core.domain.utils.Result

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    }catch (ex: UnresolvedAddressException){
        return Result.Error(NetworkError.NO_INTERNET_ERROR)
    }catch (ex: SerializationException){
        return Result.Error(NetworkError.SERIALIZATION_ERROR)
    }catch (ex:Exception){
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.UNKNOWN_ERROR)
    }

    return responseToResult(response)
}