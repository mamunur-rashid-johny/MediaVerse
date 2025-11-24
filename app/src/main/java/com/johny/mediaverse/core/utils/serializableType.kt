package com.johny.mediaverse.core.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {

    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let { parseValue(it) }
    }

    override fun parseValue(value: String): T {
        val decodedValue = Uri.decode(value)
        return json.decodeFromString(decodedValue)
    }

    override fun serializeAsValue(value: T): String {
        val jsonString = json.encodeToString(value)
        return Uri.encode(jsonString)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, serializeAsValue(value))
    }
}