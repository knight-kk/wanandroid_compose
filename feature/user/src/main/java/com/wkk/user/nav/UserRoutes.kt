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

import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wkk.user.LoginScreen
import com.wkk.user.UserScreen

object UserRoutes {
    const val MAIN = "user"
    const val LOGIN = "login"
}

@OptIn(ExperimentalLifecycleComposeApi::class)
fun NavGraphBuilder.userNav(navigateToLogin: () -> Unit, navigateUp: () -> Unit) {
    composable(UserRoutes.MAIN) {
        UserScreen(navigateToLogin = navigateToLogin)
    }
    composable(UserRoutes.LOGIN) {
        LoginScreen(onClosePage = navigateUp)
    }
}

fun NavHostController.navigateToLogin() {
    navigate(UserRoutes.LOGIN)
}
