package com.johny.mediaverse.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Created by Johny on 14/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
fun formatRuntime(minutes: Int): String {
    val h = minutes / 60
    val m = minutes % 60
    return "${h}h ${m}m"
}

fun formatCurrency(amount: Long): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(amount)
}