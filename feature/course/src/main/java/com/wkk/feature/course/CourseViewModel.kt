/*
 * Copyright 2023 knight-kk
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
package com.wkk.feature.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wkk.data.course.repository.CourseRepository
import com.wkk.model.Course
import com.wkk.model.CourseChapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
) : ViewModel() {

    val uiState: StateFlow<CourseUiState> =
        courseUiState().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CourseUiState.Loading,
        )

    private var _currentCourse: Course? = null
    private var _currentCourseChapter: CourseChapter? = null

    fun getCurrentCourse() = _currentCourse ?: Course()
    fun getCurrentCourseChapter() = _currentCourseChapter ?: CourseChapter()

    fun getChapterFlow() =
        courseRepository.getCourseChapters(getCurrentCourse().id).cachedIn(viewModelScope)

    private fun courseUiState() = courseRepository.getCourseList()
        .catch {
            CourseUiState.Error(it.message ?: "未知错误")
        }
        .map {
            CourseUiState.Success(it)
        }
        .onStart {
            CourseUiState.Loading
        }

    fun onItemClick(course: Course) {
        _currentCourse = course
    }

    fun onChapterItemClick(courseChapter: CourseChapter) {
        _currentCourseChapter = courseChapter
    }
}

sealed interface CourseUiState {
    object Loading : CourseUiState
    data class Success(val list: List<Course>) : CourseUiState
    data class Error(val message: String) : CourseUiState
}
