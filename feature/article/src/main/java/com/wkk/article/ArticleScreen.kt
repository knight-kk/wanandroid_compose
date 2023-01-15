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
package com.wkk.article

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wkk.article.components.ArticleItem
import com.wkk.model.Article
import com.wkk.model.DataResult
import kotlinx.coroutines.launch

@Composable
fun ArticleScreen(viewModel: ArticleViewModel = hiltViewModel(), onItemClick: (Article) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text(text = "首页") }) }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            ArticleList(viewModel, onItemClick)
        }
    }
}

@Composable
private fun ArticleList(viewModel: ArticleViewModel, onItemClick: (Article) -> Unit) {
    val lazyPagingItems = viewModel.pagerFlow.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn {
        items(lazyPagingItems, { it.id }) { article ->
            if (article != null) {
                ArticleItem(
                    article,
                    onItemClick,
                    onCollectionClick = {
                        coroutineScope.launch {
                            val result = viewModel.toggleCollection(article)
                            if (result is DataResult.Error) {
                                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
                Divider()
            }
        }
    }
}
