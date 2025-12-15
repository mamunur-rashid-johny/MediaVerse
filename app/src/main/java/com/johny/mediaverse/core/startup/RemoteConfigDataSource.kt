package com.johny.mediaverse.core.startup

import com.google.firebase.remoteconfig.FirebaseRemoteConfig

/**
 * Created by Johny on 15/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */
interface RemoteConfigDataSource {
    fun isAppInactive(): Boolean
    fun showSearchIcon(): Boolean
    fun showInfoFloatingButton(): Boolean
}

class RemoteConfigDataSourceImp(
    private val config: FirebaseRemoteConfig
) : RemoteConfigDataSource {
    override fun isAppInactive(): Boolean {
        return config.getBoolean("is_android_app_inactive")
    }

    override fun showSearchIcon(): Boolean {
        return config.getBoolean("show_search_icon")
    }

    override fun showInfoFloatingButton(): Boolean {
        return config.getBoolean("show_info_floating_button")
    }

}

