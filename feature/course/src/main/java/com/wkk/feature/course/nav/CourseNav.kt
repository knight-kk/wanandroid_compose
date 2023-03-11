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
package com.wkk.feature.course.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wkk.feature.course.CourseChapterDetailScreen
import com.wkk.feature.course.CourseDetailScreen
import com.wkk.feature.course.CourseScreen
import com.wkk.feature.course.CourseViewModel
import com.wkk.feature.course.nav.CourseNav.CHAPTER_DETAIL
import com.wkk.feature.course.nav.CourseNav.DETAIL
import com.wkk.feature.course.nav.CourseNav.MAIN
import com.wkk.feature.course.nav.CourseNav.ROUTE

object CourseNav {
    const val ROUTE = "course_route"
    const val MAIN = "course"
    const val DETAIL = "courseDetail"
    const val CHAPTER_DETAIL = "courseChapterDetail"
}

fun NavGraphBuilder.courseNav(navController: NavHostController) {
    navigation(MAIN, ROUTE) {
        composable(MAIN) { navBackStackEntry ->
            CourseScreen(
                viewModel = getCourseViewModel(navController, navBackStackEntry),
                navigateToCourseDetail = {
                    navController.navigate(DETAIL)
                },
            )
        }
        composable(route = DETAIL) { navBackStackEntry ->
            CourseDetailScreen(
                viewModel = getCourseViewModel(navController, navBackStackEntry),
                navigateToChapterDetail = navController::navigateToChapterDetail,
                onBack = navController::navigateUp,
            )
        }
        composable(CHAPTER_DETAIL) { navBackStackEntry ->
            CourseChapterDetailScreen(
                viewModel = getCourseViewModel(navController, navBackStackEntry),
                navigateToChapterDetail = navController::navigateToChapterDetail,
                onBack = navController::navigateUp,
            )
        }
    }
}

private fun NavHostController.navigateToChapterDetail() {
    navigate(CHAPTER_DETAIL)
}

@Composable
private fun getCourseViewModel(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry,
): CourseViewModel {
    val parentEntry = remember(navBackStackEntry) { navController.getBackStackEntry(ROUTE) }
    return hiltViewModel(parentEntry)
}
