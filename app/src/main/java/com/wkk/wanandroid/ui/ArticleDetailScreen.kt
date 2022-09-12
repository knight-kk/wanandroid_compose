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
package com.wkk.wanandroid.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wkk.wanandroid.ui.components.CommonTopBar
import com.wkk.wanandroid.ui.components.WebView

@Composable
fun ArticleDetailScreen(title: String, url: String, onBack: () -> Unit) {
    Scaffold(
        topBar = { CommonTopBar(title, onBack) }
    ) { paddingValues ->
        WebView(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            url
        )
    }
}
