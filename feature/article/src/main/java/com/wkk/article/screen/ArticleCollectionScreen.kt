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
package com.wkk.article.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wkk.article.components.ArticleItem
import com.wkk.model.Article
import com.wkk.ui.components.LoadFooter
import com.wkk.ui.components.appTopBar
import kotlinx.coroutines.launch

@Composable
fun ArticleCollectionScreen(
    viewModel: ArticleCollectionViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToArticleDetail: (Article) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier=Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = appTopBar("收藏", navigateUp,scrollBehavior),
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
            ArticleList(
                modifier = Modifier.fillMaxSize(),
                articleCollectionData = data,
                onItemClick = navigateToArticleDetail,
                onLoadMoreData = onLoadMoreData,
            )
        }
    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articleCollectionData: ArticleCollectionData,
    onItemClick: (Article) -> Unit,
    onLoadMoreData: () -> Unit,

) {
    val articles = articleCollectionData.articles
    LazyColumn(modifier) {
        items(articles) { article ->
            ArticleItem(
                article = article,
                onItemClick = {
                    onItemClick(article)
                },
            ) {
            }
        }
        item {
            LoadFooter(
                isLoading = articleCollectionData.isLoading,
                isDataEnd = articleCollectionData.isDataEnd,
                onLoadMoreData = onLoadMoreData,
            )
        }
    }
}
