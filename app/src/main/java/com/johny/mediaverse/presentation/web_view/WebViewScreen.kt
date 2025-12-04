package com.johny.mediaverse.presentation.web_view

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.johny.mediaverse.presentation.web_view.componets.WebViewShimmer

@Composable
fun WebViewScreen(
    modifier: Modifier = Modifier,
    state: WebviewState,
    onIntent: (WebViewIntent) -> Unit
) {
    val context = LocalContext.current
    val webview = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
        }
    }

    BackHandler(enabled = webview.canGoBack()) {
        webview.goBack()
    }

    LaunchedEffect(Unit) {
        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (!state.pageFirstLoaded){
                    onIntent(WebViewIntent.OnLoadingIntent(true))
                    onIntent(WebViewIntent.OnFirstPageLoadIntent(true))
                }

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                onIntent(WebViewIntent.OnLoadingIntent(false))
            }
        }
    }


    LaunchedEffect(state.url) {
        if (state.url != null && webview.url != state.url) {
            webview.loadUrl(state.url)
        }
    }


    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            WebViewShimmer()
        }
    } else {


        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = {
                        if (webview.canGoBack()) {
                            webview.goBack()
                        } else {
                            onIntent(WebViewIntent.OnBackPressedIntent)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }

                Text(
                    text = state.title ?: "Loading...",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { webview }
            )
        }
    }
}