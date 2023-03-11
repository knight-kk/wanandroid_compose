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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.wkk.feature.course.components.CourseDirectory
import com.wkk.feature.course.components.CourseItem

@Composable
fun CourseDetailScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    navigateToChapterDetail: () -> Unit = { },
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
            CourseDirectory(chapters = chapters, onItemClick = {
                viewModel.onChapterItemClick(it)
                navigateToChapterDetail()
            })
        }
    }
}

private fun topBar(title: String, onBack: () -> Unit): @Composable () -> Unit = {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
            }
        },
        title = { Text(text = title) },
    )
}

@Preview
@Composable
fun CourseDetailPreview() {
    CourseDetailScreen()
}
