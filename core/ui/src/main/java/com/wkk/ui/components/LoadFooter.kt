/*
 * Copyright 2023 knight-kk
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
package com.wkk.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class LoadFooterUiData(
    val loadingMessage: String = "Loading...",
    val loadEndMessage: String = "加载完成",
    val loadMoreMessage: String = "加载更多",
)

@Composable
fun LoadFooter(
    modifier: Modifier = Modifier,
    uiData: LoadFooterUiData = LoadFooterUiData(),
    isLoading: Boolean,
    isDataEnd: Boolean,
    onLoadMoreData: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            Text(text = uiData.loadingMessage)
            return
        }
        if (isDataEnd) {
            Text(text = uiData.loadEndMessage)
            return
        }
        Button(onClick = onLoadMoreData) {
            Text(text = uiData.loadMoreMessage)
        }
    }
}
