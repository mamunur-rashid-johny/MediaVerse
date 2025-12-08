package com.johny.mediaverse.presentation.web_view

sealed interface WebViewIntent {
    data object OnBackPressedIntent: WebViewIntent
    data class OnLoadingIntent(val isLoading: Boolean): WebViewIntent
}