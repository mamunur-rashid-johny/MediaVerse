package com.johny.mediaverse.presentation.on_board

sealed interface OnBoardEffect {
    data object NavigateToHome: OnBoardEffect
}