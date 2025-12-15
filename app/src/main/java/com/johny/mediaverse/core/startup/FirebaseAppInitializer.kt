package com.johny.mediaverse.core.startup

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.initialize

/**
 * Created by Johny on 15/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */
class FirebaseAppInitializer : Initializer<FirebaseApp> {
    override fun create(context: Context): FirebaseApp {
        return Firebase.initialize(context)!!
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}