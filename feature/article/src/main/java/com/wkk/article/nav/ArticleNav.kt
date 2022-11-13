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
package com.wkk.article.nav

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wkk.article.ArticleDetailScreen
import com.wkk.article.ArticleScreen
import com.wkk.article.nav.ArticleRoutes.DETAIL
import com.wkk.article.nav.ArticleRoutes.MAIN

object ArticleRoutes {
    const val MAIN = "article"

    object DETAIL {
        const val ARGUMENT_TITLE = "title"
        const val ARGUMENT_URL = "url"

        val NAME: String = crateParams("{$ARGUMENT_TITLE}", "{$ARGUMENT_URL}")

        fun crateParams(title: String, url: String) = "articleDetail/$title/$url"
    }
}

fun NavGraphBuilder.articleNav(
    navigateToArticleDetail: (title: String, url: String) -> Unit,
    navigateUp: () -> Unit
) {
    composable(MAIN) {
        ArticleScreen(onItemClick = { navigateToArticleDetail(it.title, it.link) })
    }
    composable(DETAIL.NAME) { navBackStackEntry ->
        val arguments = navBackStackEntry.arguments ?: return@composable
        ArticleDetailScreen(
            title = arguments.getString(DETAIL.ARGUMENT_TITLE) ?: "",
            url = arguments.getString(DETAIL.ARGUMENT_URL) ?: "",
            onBack = navigateUp
        )
    }
}

fun NavHostController.navigateToArticleDetail(title: String, url: String) {
    navigate(DETAIL.crateParams(Uri.encode(title), Uri.encode(url)))
}
