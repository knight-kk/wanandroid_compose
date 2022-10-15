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
package com.wkk.wanandroid.ui.home.components

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wkk.wanandroid.model.Article

@Composable
fun ArticleItem(
    article: Article,
    isCollection: Boolean,
    onItemClick: (Article) -> Unit,
    onCollectionClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(article) }),
        overlineText = {
            Text(text = article.author.takeIf { it.isNotEmpty() } ?: article.shareUser)
        },
        headlineText = { Text(text = article.title) },
        supportingText = {
            Text(text = DateFormat.format("yyyy-MM-dd HH:mm:ss", article.publishTime).toString())
        },
        leadingContent = {
            IconButton(onClick = onCollectionClick) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    tint = if (isCollection) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    contentDescription = "collection"
                )
            }
        }
    )
}

@Preview
@Composable
fun ArticleItemPreview() {
    ArticleItem(Article(), false, {}, {})
}
