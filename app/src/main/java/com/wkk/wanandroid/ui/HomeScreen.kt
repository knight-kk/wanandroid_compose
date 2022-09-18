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

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wkk.wanandroid.model.Article
import com.wkk.wanandroid.vm.HomeViewModel

/**
 * 首页
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel, onItemClick: (Article) -> Unit) {
    ArticleList(viewModel, onItemClick)
}

@Composable
fun ArticleList(viewModel: HomeViewModel, onItemClick: (Article) -> Unit) {
    val lazyPagingItems = viewModel.pagerFlow.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyPagingItems, { it.id }) { article ->
            if (article != null) {
                ArticleItem(article, onItemClick)
                Divider()
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, onItemClick: (Article) -> Unit) {
    Column(
        Modifier
            .clickable(onClick = { onItemClick(article) })
            .padding(10.dp)
    ) {
        Text(text = article.title)
        Text(text = article.author)
        Text(text = DateFormat.format("yyyy-MM-dd HH:mm:ss", article.publishTime).toString())
    }
}

@Preview(showBackground = false)
@Composable
fun HomePreView() {
    ArticleItem(Article()) {}
}
