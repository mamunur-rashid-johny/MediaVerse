package com.johny.mediaverse.presentation.web_view.utils

import android.webkit.JavascriptInterface

class WebAppInterface(private val onMessageReceived: (String) -> Unit) {
    @JavascriptInterface
    fun sendMessage(message: String) {
        onMessageReceived(message)
    }
}