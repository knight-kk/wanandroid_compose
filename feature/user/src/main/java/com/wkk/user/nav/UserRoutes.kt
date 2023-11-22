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
package com.wkk.user.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wkk.model.AppInfo
import com.wkk.user.screen.AboutScreen
import com.wkk.user.screen.ReadHistoryScreen
import com.wkk.user.screen.UserCoinScreen
import com.wkk.user.screen.UserScreen
import com.wkk.user.screen.login.LoginScreen

object UserRoutes {
    const val MAIN = "user"
    const val LOGIN = "login"
    const val READ_HISTORY = "read_history"
    const val USER_COIN = "user_coin"
    const val ABOUT_US = "about_us"
}

fun NavGraphBuilder.userNav(
    appInfo: AppInfo,
    navigateToLogin: () -> Unit,
    navigateUp: () -> Unit,
    navigate: (route: String) -> Unit,
    navigateToArticleCollection: () -> Unit = {},
) {
    composable(UserRoutes.MAIN) {
        UserScreen(
            navigateToLogin = navigateToLogin,
            navigateToItem = navigate,
            navigateToArticleCollection = navigateToArticleCollection,
        )
    }
    composable(UserRoutes.LOGIN) {
        LoginScreen(onClosePage = navigateUp)
    }
    composable(UserRoutes.READ_HISTORY) {
        ReadHistoryScreen(navigateUp = navigateUp)
    }
    composable(UserRoutes.USER_COIN) {
        UserCoinScreen(navigateUp = navigateUp)
    }
    composable(UserRoutes.ABOUT_US) {
        AboutScreen(appInfo, navigateUp = navigateUp, navigate)
    }
}

fun NavHostController.navigateToLogin() {
    navigate(UserRoutes.LOGIN)
}

fun NavHostController.navigateTo(routeName: String) {
    navigate(routeName)
}
