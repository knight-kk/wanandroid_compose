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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wkk.wanandroid.ui.ArticleDetailScreen
import com.wkk.wanandroid.ui.HomeScreen
import com.wkk.wanandroid.ui.UserScreen
import com.wkk.wanandroid.ui.login.LoginScreen
import com.wkk.wanandroid.vm.HomeViewModel
import java.net.URLEncoder

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val viewModel = viewModel<HomeViewModel>()
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Main.HOME,
        modifier = modifier
    ) {

        composable(AppDestinations.Account.LOGIN) {
            LoginScreen()
        }

        composable(AppDestinations.Main.HOME) {
            HomeScreen(viewModel) {
                navController.navigate(
                    AppDestinations.ArticleDetail.getValue(
                        it.title,
                        URLEncoder.encode(it.link, Charsets.UTF_8.name())
                    )
                )
            }
        }
        composable(AppDestinations.Main.USER) { UserScreen { navController.navigate(AppDestinations.Account.LOGIN) } }

        composable(AppDestinations.ArticleDetail.getValue()) { navBackStackEntry ->
            val arguments = navBackStackEntry.arguments ?: return@composable
            ArticleDetailScreen(
                arguments.getString(AppDestinations.ArticleDetail.ARGUMENT_TITLE) ?: "",
                arguments.getString(AppDestinations.ArticleDetail.ARGUMENT_URL) ?: ""
            ) {
                navController.navigateUp()
            }
        }
    }
}
