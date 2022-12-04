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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wkk.article.nav.ArticleRoutes

@Composable
fun WanAndroidApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val currentRoute = navController
                .currentBackStackEntryAsState().value?.destination?.route ?: return@Scaffold
            val shouldShowBottomBar = currentRoute in HomeSections.values().map { it.route }
            if (shouldShowBottomBar) HomeBottomNav(currentRoute) {
                navigateToBottomBarRoute(
                    navController,
                    it.route
                )
            }
        }
    ) { innerPaddingValue ->
        AppNavGraph(
            Modifier.padding(innerPaddingValue),
            navController
        )
    }
}

@Composable
private fun HomeBottomNav(
    currentRoute: String,
    onItemSelected: (section: HomeSections) -> Unit
) {
    NavigationBar {
        val items = HomeSections.values()
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = "") },
                label = { Text(text = stringResource(item.title)) },
                selected = currentRoute == item.route,
                onClick = { onItemSelected(item) }
            )
        }
    }
}

fun navigateToBottomBarRoute(navController: NavController, route: String) {
    val currentRoute = navController.currentDestination?.route
    if (route != currentRoute) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(findStartDestination(navController.graph).id) {
                saveState = true
            }
        }
    }
}

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

private enum class HomeSections(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    INDEX(ArticleRoutes.MAIN, R.string.main_tab_article, R.drawable.icon_outline_article_24px),
}

@Preview
@Composable
fun MainScreenPreview() {
    WanAndroidApp()
}
