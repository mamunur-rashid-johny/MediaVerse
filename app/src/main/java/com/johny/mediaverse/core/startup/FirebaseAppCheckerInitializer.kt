package com.johny.mediaverse.core.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.Firebase
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.johny.mediaverse.BuildConfig

/**
 * Created by Johny on 15/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */
class FirebaseAppCheckerInitializer : Initializer<FirebaseAppCheck> {
    override fun create(context: Context): FirebaseAppCheck {
        val appCheck = Firebase.appCheck.apply {
            if (BuildConfig.DEBUG) {
                Log.d(
                    "AppCheck",
                    "Installing Firebase debug, ensure your " +
                            "debug token is registered on Firebase Console",
                )
                installAppCheckProviderFactory(
                    DebugAppCheckProviderFactory.getInstance()
                )
            } else {
                Log.d("AppCheck", "Play integrity installing...")
                installAppCheckProviderFactory(
                    PlayIntegrityAppCheckProviderFactory.getInstance()
                )
            }
            setTokenAutoRefreshEnabled(true)
        }
        val token = appCheck.getAppCheckToken(true)
        token.addOnSuccessListener {
            if (token.isSuccessful) {
                Log.d("AppCheck","Firebase app check token success: ${token.result.expireTimeMillis}")
            } else {
                Log.e("AppCheck", "${token.exception} Firebase app check token failure")
            }
        }
        return appCheck
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return listOf(FirebaseAppInitializer::class.java)
    }
}