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
package com.wkk.feature.course.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.wkk.model.CourseChapter

@Composable
internal fun CourseDirectory(
    modifier: Modifier = Modifier,
    chapters: LazyPagingItems<CourseChapter>,
    onItemClick: (CourseChapter) -> Unit = { },
) {
    Column(modifier) {
        Row(modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = Icons.Default.FormatListBulleted,
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = "目录",
            )
            Text(text = "目录", modifier = Modifier.padding(horizontal = 10.dp))
        }

        LazyColumn {
            itemsIndexed(chapters, key = { _, chapter -> chapter.id }) { index, chapter ->
                DirectoryItem(
                    name = "${index + 1}. ${chapter?.name ?: ""}",
                    onCLick = {
                        if (chapter == null) return@DirectoryItem
                        onItemClick(chapter)
                    },
                )
                Divider()
            }
        }
    }
}

@Composable
private fun DirectoryItem(name: String, onCLick: () -> Unit) {
    Text(
        text = name,
        Modifier
            .clickable(onClick = onCLick)
            .fillMaxWidth()
            .padding(10.dp),
        maxLines = 1,
    )
}
