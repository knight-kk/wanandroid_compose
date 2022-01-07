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
package com.wkk.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.wkk.wanandroid.ui.HomeScreen
import com.wkk.wanandroid.ui.QAScreen
import com.wkk.wanandroid.ui.TreeScreen
import com.wkk.wanandroid.ui.UserScreen
import com.wkk.wanandroid.ui.theme.WanandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidApp()
        }
    }
}

@Composable
private fun WanAndroidApp() {
    WanandroidTheme {
        // [转态管理](https://developer.android.google.cn/jetpack/compose/state)
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
}

@Composable
private fun Toolbar(title: String = stringResource(id = R.string.app_name)) =
    TopAppBar(title = { Text(text = title) })

@Composable
private fun HomeBottomNav(
    currentSection: HomeSections,
    onItemSelected: (section: HomeSections) -> Unit,
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
        val items = HomeSections.values()
        items.forEach { homeSection ->
            val selected = homeSection == currentSection
            val tintColor =
                if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
            BottomNavigationItem(
                icon = { Icon(imageVector = homeSection.icon, contentDescription = "", tint = tintColor) },
                selected = selected,
                label = {
                    Text(
                        text = stringResource(homeSection.title),
                        style = TextStyle(color = tintColor)
                    )
                },
                onClick = { onItemSelected(homeSection) }
            )
        }
    }
}

private enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    INDEX(R.string.main_tab_home, Icons.Outlined.Home),
    TREE(R.string.main_tab_qa, Icons.Outlined.Home),
    QA(R.string.main_tab_tree, Icons.Outlined.Home),
    USER(R.string.main_tab_user, Icons.Outlined.Person)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidApp()
}
