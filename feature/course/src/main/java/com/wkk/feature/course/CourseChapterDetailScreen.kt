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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.wkk.feature.course.components.CourseDirectory
import com.wkk.model.CourseChapter
import com.wkk.ui.theme.components.WebView
import kotlinx.coroutines.launch

@Composable
fun CourseChapterDetailScreen(
    viewModel: CourseViewModel,
    navigateToChapterDetail: () -> Unit = { },
    onBack: () -> Unit = {},
) {
    val chapter = remember(viewModel) { viewModel.getCurrentCourseChapter() }
    val chapters = remember(viewModel) { viewModel.getChapterFlow() }.collectAsLazyPagingItems()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = drawerContent(
            chapters = chapters,
            onItemClick = {
                viewModel.onChapterItemClick(it)
                navigateToChapterDetail()
            },
        ),
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = chapter.name,
                    onNavigation = onBack,
                    onActionClick = { scope.launch { drawerState.open() } },
                )
            },
        ) { paddingValues ->
            CourseChapterDetailContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                url = chapter.link,
            )
        }
    }
}

@Composable
private fun CourseChapterDetailContent(modifier: Modifier, url: String) {
    WebView(modifier, url)
}

private fun drawerContent(
    chapters: LazyPagingItems<CourseChapter>,

    onItemClick: (CourseChapter) -> Unit,
): @Composable () -> Unit = {
    ModalDrawerSheet(Modifier) {
        CourseDirectory(
            modifier = Modifier.fillMaxWidth(0.7f),
            chapters,
            onItemClick = onItemClick,
        )
    }
}

@Composable
fun TopBar(
    title: String,
    onNavigation: () -> Unit,
    onActionClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigation) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(imageVector = Icons.Default.FormatListBulleted, contentDescription = "")
            }
        },
    )
}
