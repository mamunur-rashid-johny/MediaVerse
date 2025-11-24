package com.johny.mediaverse.presentation.on_board

sealed interface OnBoardIntent {
    data object SaveOnBoardIntent : OnBoardIntent
}