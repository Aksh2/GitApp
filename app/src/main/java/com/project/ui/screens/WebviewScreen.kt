package com.project.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.project.ui.baseui.BaseScaffold

@Composable
fun WebviewScreen(url: String, onBackPress: () -> Unit) {
    BaseScaffold("Repository details", onBackPress) {
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
                        return if (strurl != null && strurl.startsWith("https://api.github.com")) {
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