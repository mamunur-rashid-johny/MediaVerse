package com.johny.mediaverse.core.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.johny.mediaverse.core.utils.Constants

class PreferenceManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.Miscellaneous.PREF_NAME, Context.MODE_PRIVATE)

    fun <T> put(key: String, value: T) {
        val editor = prefs.edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            else -> throw IllegalArgumentException("This type cannot be saved to Prefs")
        }

        editor.apply()
    }

    internal inline fun <reified T> get(key: String, defaultValue: T): T {
        return when (T::class) {
            String::class -> prefs.getString(key, defaultValue as String) as T
            Int::class -> prefs.getInt(key, defaultValue as Int) as T
            Boolean::class -> prefs.getBoolean(key, defaultValue as Boolean) as T
            Long::class -> prefs.getLong(key, defaultValue as Long) as T
            Float::class -> prefs.getFloat(key, defaultValue as Float) as T
            else -> throw IllegalArgumentException("This type cannot be retrieved from Prefs")
        }
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}