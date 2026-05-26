package com.johny.mediaverse.core.presentation.utils

import android.content.Context
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.R

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.RequestTimeout -> R.string.error_request_timeout
        NetworkError.TooManyRequest -> R.string.error_too_many_request
        NetworkError.NoInternet -> R.string.error_no_internet
        is NetworkError.ServerError -> R.string.error_unknown
        NetworkError.SerializationError -> R.string.error_serialize
        is NetworkError.Unknown -> R.string.error_unknown
    }
    return context.getString(resId)
}