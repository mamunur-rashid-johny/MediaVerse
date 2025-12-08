package com.johny.mediaverse.presentation.web_view

data class WebviewState(
    val url: String?=null,
    val title: String?=null,
    val isLoading: Boolean = true
)
