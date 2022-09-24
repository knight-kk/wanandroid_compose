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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wkk.wanandroid.model.Article
import com.wkk.wanandroid.vm.HomeViewModel

/**
 * 首页
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel, onItemClick: (Article) -> Unit) {
    Scaffold(topBar = { SmallTopAppBar(title = { Text(text = "首页") }) }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            ArticleList(viewModel, onItemClick)
        }
    }
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
    val (isCollection, setCollection) = remember { mutableStateOf(article.collect) }
    ListItem(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { onItemClick(article) }),
        overlineText = {
            Text(text = article.author.takeIf { it.isNotEmpty() } ?: article.shareUser)
        },
        headlineText = { Text(text = article.title) },
        supportingText = {
            Text(text = DateFormat.format("yyyy-MM-dd HH:mm:ss", article.publishTime).toString())
        },
        leadingContent = {
            IconButton(onClick = { setCollection(isCollection.not()) }) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    tint = if (isCollection) MaterialTheme.colorScheme.primary else Color.Black,
                    contentDescription = "collection"
                )
            }
        }
    )
}

@Preview(showBackground = false)
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        Article(
            title = "文章标题",
            author = "knight-kk",
            publishTime = System.currentTimeMillis()
        )
    ) {}
}
