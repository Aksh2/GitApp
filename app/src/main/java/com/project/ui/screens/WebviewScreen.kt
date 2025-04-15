package com.project.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.project.data.Constants.BASE_URL
import com.project.data.Constants.REPOSITORY_DETAILS
import com.project.ui.baseui.BaseScaffold

@Composable
fun WebviewScreen(url: String, onBackPress: () -> Unit) {
    BaseScaffold(title = REPOSITORY_DETAILS, onBackPress = onBackPress) {
        Column(modifier = it) {
            Webview(url)
        }
    }
}

@Composable
fun Webview(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: android.webkit.WebResourceRequest?
                    ): Boolean {
                        val strurl = request?.url?.toString()
                        return if (strurl != null && strurl.startsWith(BASE_URL)) {
                            false // Load within the WebView
                        } else {
                            // Open in system browser (you'll need to handle this)
                            true
                        }
                    }
                }
                loadUrl(url)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}