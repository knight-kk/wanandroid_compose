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
package com.wkk.wanandroid.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wkk.wanandroid.model.Article
import com.wkk.wanandroid.ui.home.components.ArticleItem
import com.wkk.wanandroid.ui.home.vm.HomeViewModel
import kotlinx.coroutines.launch

/**
 * 首页
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onItemClick: (Article) -> Unit) {
    Scaffold(topBar = { SmallTopAppBar(title = { Text(text = "首页") }) }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            ArticleList(viewModel, onItemClick)
        }
    }
}

@Composable
private fun ArticleList(viewModel: HomeViewModel, onItemClick: (Article) -> Unit) {
    val lazyPagingItems = viewModel.pagerFlow.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn {
        items(lazyPagingItems, { it.id }) { article ->
            var isCollection by remember { mutableStateOf(article?.collect ?: false) }
            if (article != null) {
                ArticleItem(
                    article,
                    isCollection = isCollection,
                    onItemClick,
                    onCollectionClick = {
                        coroutineScope.launch {
                            val result = viewModel.toggleCollection(article)
                            if (result.isSuccess()) {
                                isCollection = !isCollection
                            } else {
                                Toast.makeText(context, result.errorMsg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
                Divider()
            }
        }
    }
}
