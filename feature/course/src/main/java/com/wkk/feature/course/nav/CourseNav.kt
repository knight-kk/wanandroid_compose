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

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wkk.feature.course.CourseScreen
import com.wkk.feature.course.nav.CourseNav.MAIN

object CourseNav {
    const val MAIN = "course"
}

fun NavGraphBuilder.courseNav() {
    composable(MAIN) {
        CourseScreen()
    }
}
