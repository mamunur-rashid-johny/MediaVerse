package com.johny.mediaverse.core.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.johny.mediaverse.R

/**
 * Created by Johny on 15/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */
class FirebaseRemoteConfigInitializer : Initializer<FirebaseRemoteConfig> {
    override fun create(context: Context): FirebaseRemoteConfig {
        return Firebase.remoteConfig.apply {
            val configSetting = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 600
            }
            setConfigSettingsAsync(configSetting)
            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate()
                .addOnSuccessListener {
                    Log.d("RemoteConfig","Data Fetched $it")
                }
                .addOnFailureListener {
                    Log.e("RemoteConfig","Unable to Fetch Data $it")
                }
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return listOf(FirebaseAppInitializer::class.java)
    }

}