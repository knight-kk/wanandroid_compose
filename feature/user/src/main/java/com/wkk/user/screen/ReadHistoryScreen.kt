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
package com.wkk.user.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wkk.model.ArticleHistory
import com.wkk.ui.components.LoadFooter
import com.wkk.ui.components.appTopBar
import kotlinx.coroutines.launch

@Composable
fun ReadHistoryScreen(
    viewModel: ReadHistoryViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = appTopBar("浏览记录", navigateUp, scrollBehavior),
    ) { paddingValues ->
        val data = viewModel.uiDataState.collectAsStateWithLifecycle().value
        Box(Modifier.padding(paddingValues)) {
            LaunchedEffect(Unit) {
                viewModel.fetchData()
            }
            val rememberCoroutineScope = rememberCoroutineScope()
            val onLoadMoreData: () -> Unit = {
                rememberCoroutineScope.launch {
                    viewModel.fetchData()
                }
            }
            ReadHistoryList(
                modifier = Modifier.fillMaxSize(),
                articleHistoryData = data,
                onLoadMoreData = onLoadMoreData,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadHistoryList(
    modifier: Modifier = Modifier,
    articleHistoryData: ArticleHistoryData,
    onLoadMoreData: () -> Unit,
) {
    val historyData = articleHistoryData.historyData
    LazyColumn(modifier = modifier) {
        for ((key, list) in historyData) {
            stickyHeader(key = key) { ReadHistorySection(key) }
            items(list, key = { it.id }) { history ->
                ReadHistoryItem(history)
                Divider()
            }
        }
        item {
            LoadFooter(
                isLoading = articleHistoryData.isLoading,
                isDataEnd = articleHistoryData.isDataEnd,
                onLoadMoreData = onLoadMoreData,
            )
        }
    }
}

@Composable
fun ReadHistorySection(name: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        text = name,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
fun ReadHistoryItem(
    articleHistory: ArticleHistory,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
    ) {
        Text(
            text = articleHistory.author,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
        )
        Text(
            text = articleHistory.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = articleHistory.category,
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
