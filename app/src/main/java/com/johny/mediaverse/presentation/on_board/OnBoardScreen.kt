package com.johny.mediaverse.presentation.on_board

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import kotlinx.coroutines.launch

@Composable
fun OnBoardScreen(
    modifier: Modifier = Modifier,
    state: OnBoardState,
    onBoardEvent: (OnBoardIntent) -> Unit
) {

    if (state.onBoardInfo.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { state.onBoardInfo.size })
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PagerScreen(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pagerState = pagerState,
            pagerItem = state.onBoardInfo
        )

        Spacer(modifier = Modifier.size(40.dp))
        val isLastPage = pagerState.currentPage == state.onBoardInfo.size - 1
        NextOrGetStartedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = if (pagerState.currentPage < state.onBoardInfo.size - 1) "Get Started" else "Finish"
        ) {
            if (isLastPage) {
                onBoardEvent(OnBoardIntent.SaveOnBoardIntent)
            } else {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Preview
@Composable
private fun OnBoardScreenPreview() {
    MediaVerseTheme {
        val state = OnBoardState(
            onBoardInfo = dataSets
        )
        OnBoardScreen(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onBoardEvent = {}
        )
    }
}