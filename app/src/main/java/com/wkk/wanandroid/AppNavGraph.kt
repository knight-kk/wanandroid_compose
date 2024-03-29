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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.wkk.article.nav.ArticleRoutes
import com.wkk.article.nav.articleNav
import com.wkk.article.nav.navigateToArticleCollection
import com.wkk.article.nav.navigateToArticleDetail
import com.wkk.feature.course.nav.courseNav
import com.wkk.user.nav.navigateTo
import com.wkk.user.nav.navigateToLogin
import com.wkk.user.nav.userNav

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ArticleRoutes.MAIN,
        modifier = modifier,
    ) {
        articleNav(
            navigateToArticleDetail = navController::navigateToArticleDetail,
            navigateUp = navController::navigateUp,
        )

        courseNav(navController)

        userNav(
            navigateToLogin = navController::navigateToLogin,
            navigateUp = navController::navigateUp,
            navigate = navController::navigateTo,
            navigateToArticleCollection = navController::navigateToArticleCollection,
        )
    }
}
