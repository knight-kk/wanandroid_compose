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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wkk.feature.course.components.CourseItem
import com.wkk.model.Course
import com.wkk.ui.theme.AppTheme

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CourseScreen(viewModel: CourseViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Surface {
        CourseScreen(uiState)
    }
}

@Composable
fun CourseScreen(uiState: CourseUiState) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopAppBar(title = { Text(text = "教程") })
        when (uiState) {
            is CourseUiState.Error -> ErrorView(uiState.message)
            is CourseUiState.Loading -> LoadingView()
            is CourseUiState.Success -> CourseContent(uiState.list)
        }
    }
}

@Composable
fun CourseContent(courses: List<Course>) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(courses, key = { it.id }) { course ->
            CourseItem(course, Modifier.padding(10.dp)) {}
            Divider()
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(errorMessage: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = errorMessage)
    }
}

@Preview
@Composable
fun CourseScreenPreView() {
    AppTheme {
        CourseScreen(
            CourseUiState.Success(
                List(10) {
                    Course(
                        id = it.toString(),
                        name = "阮一峰",
                        author = "阮一峰",
                        desc = "C 语言入门教程。",
                    )
                },
            ),
        )
    }
}
