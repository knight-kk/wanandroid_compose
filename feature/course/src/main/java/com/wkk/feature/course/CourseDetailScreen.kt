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
package com.wkk.feature.course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.wkk.feature.course.components.CourseItem
import com.wkk.model.CourseChapter

@Composable
fun CourseDetailScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    navigateToChapterDetail: (title: String, link: String) -> Unit = { _, _ -> },
    onBack: () -> Unit = {},
) {
    val course = remember(viewModel) { viewModel.getCurrentCourse() }
    val chapters = remember(viewModel) { viewModel.getChapterFlow() }.collectAsLazyPagingItems()
    Scaffold(
        topBar = topBar(course.name, onBack),
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CourseItem(
                course = course,
                Modifier
                    .padding(10.dp)
                    .clickable(enabled = false, onClick = {}),
            ) {}

            Row(modifier = Modifier.padding(10.dp)) {
                Icon(
                    imageVector = Icons.Default.FormatListBulleted,
                    tint = MaterialTheme.colorScheme.outline,
                    contentDescription = "目录",
                )
                Text(text = "目录", modifier = Modifier.padding(horizontal = 10.dp))
            }
            DirectoryList(chapters, navigateToChapterDetail)
        }
    }
}

fun topBar(title: String, onBack: () -> Unit): @Composable () -> Unit {
    return {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            },
            title = { Text(text = title) },
        )
    }
}

@Composable
fun DirectoryList(
    chapters: LazyPagingItems<CourseChapter>,
    navigateToChapterDetail: (title: String, link: String) -> Unit = { _, _ -> },
) {
    LazyColumn {
        itemsIndexed(chapters, key = { _, chapter -> chapter.id }) { index, chapter ->
            Text(
                text = "${index + 1}. ${chapter?.name ?: ""}",
                Modifier
                    .clickable {
                        if (chapter == null) return@clickable
                        navigateToChapterDetail(chapter.name, chapter.link)
                    }
                    .fillMaxWidth()
                    .padding(10.dp),
            )
            Divider()
        }
    }
}

@Preview
@Composable
fun CourseDetailPreview() {
    CourseDetailScreen()
}
