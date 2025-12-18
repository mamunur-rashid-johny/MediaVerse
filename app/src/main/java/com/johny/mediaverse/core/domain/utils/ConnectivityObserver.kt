package com.johny.mediaverse.core.domain.utils

import kotlinx.coroutines.flow.Flow

/**
 * Created by Johny on 17/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */
interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}