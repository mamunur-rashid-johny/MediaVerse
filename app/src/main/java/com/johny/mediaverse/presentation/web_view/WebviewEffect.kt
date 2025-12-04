package com.johny.mediaverse.presentation.web_view

sealed interface WebviewEffect {
    data object OnBackPressedEffect: WebviewEffect
}