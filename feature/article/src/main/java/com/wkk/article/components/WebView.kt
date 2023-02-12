/*
 * Copyright 2022 knight-kk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wkk.article.components

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(modifier: Modifier = Modifier, url: String) {
    val urlAddress by remember { mutableStateOf(url) }
    val state = rememberWebViewState(urlAddress)
    val loadingState = state.loadingState
    Box(modifier) {
//        WebView.setWebContentsDebuggingEnabled(true)
        WebView(
            state = state,
            onCreated = { webView: WebView ->
                webView.settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }
            },
        )

        if (loadingState is LoadingState.Loading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = loadingState.progress,
            )
        }
    }
}
