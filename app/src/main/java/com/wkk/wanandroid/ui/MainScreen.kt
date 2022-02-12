/*
 * Copyright 2021 knight-kk
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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.wkk.wanandroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var currentSection by remember { mutableStateOf(HomeSections.INDEX) }
    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { HomeBottomNav(currentSection) { currentSection = it } },
    ) {
        Crossfade(currentSection) {
            when (it) {
                HomeSections.INDEX -> HomeScreen()
                HomeSections.QA -> QAScreen()
                HomeSections.TREE -> TreeScreen()
                HomeSections.USER -> UserScreen()
            }
        }
    }
}

@Composable
private fun Toolbar(title: String = stringResource(id = R.string.app_name)) =
    SmallTopAppBar(title = { Text(text = title) })

@Composable
private fun HomeBottomNav(
    currentSection: HomeSections,
    onItemSelected: (section: HomeSections) -> Unit,
) {
    NavigationBar {
        val items = HomeSections.values()
        items.forEach { item ->
            val selected = item == currentSection
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = "") },
                label = { Text(text = stringResource(item.title),) },
                selected = selected,
                onClick = { onItemSelected(item) }
            )
        }
    }
}

private enum class HomeSections(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    INDEX(R.string.main_tab_home, R.drawable.ic_outline_home_24),
    TREE(R.string.main_tab_qa, R.drawable.ic_outline_home_24),
    QA(R.string.main_tab_tree, R.drawable.ic_outline_home_24),
    USER(R.string.main_tab_user, R.drawable.ic_outline_home_24)
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
