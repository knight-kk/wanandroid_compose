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
package com.wkk.article.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wkk.article.components.ArticleItem
import com.wkk.model.Article
import com.wkk.model.DataResult
import kotlinx.coroutines.launch

@Composable
fun ArticleScreen(viewModel: ArticleViewModel = hiltViewModel(), onItemClick: (Article) -> Unit) {
    val lazyListState = rememberLazyListState()
    val isTop = remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 &&
                lazyListState.firstVisibleItemScrollOffset == 0
        }
    }.value
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "首页") },
            colors = topAppBarColors(
                MaterialTheme.colorScheme.surfaceColorAtElevation(if (isTop) 0.dp else 3.dp),
            ),
        )
    }) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues),
        ) {
            ArticleList(viewModel, lazyListState, onItemClick)
        }
    }
}

@Composable
private fun ArticleList(
    viewModel: ArticleViewModel,
    lazyListState: LazyListState = rememberLazyListState(),
    onItemClick: (Article) -> Unit,
) {
    val lazyPagingItems = viewModel.pagerFlow.collectAsLazyPagingItems()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val onItemClickWrapper: (Article) -> Unit = { article ->
        coroutineScope.launch { viewModel.readArticle(article) }
        onItemClick(article)
    }

    LazyColumn(state = lazyListState) {
        items(lazyPagingItems, { it.id }) { article ->
            if (article == null) return@items
            ArticleItem(
                article,
                onItemClickWrapper,
                onCollectionClick = {
                    coroutineScope.launch {
                        val result = viewModel.toggleCollection(article)
                        if (result is DataResult.Error) {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
            )
            Divider()
        }
        footer(lazyPagingItems.loadState.append)
    }
}

private fun LazyListScope.footer(appendLoadState: LoadState) {
    if (appendLoadState != LoadState.Loading) return
    item(key = "footer") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator()
            Text(modifier = Modifier.padding(top = 2.dp), text = "加载中……")
        }
    }
}
