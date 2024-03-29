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
package com.wkk.network.datasource.impl

import com.wkk.network.ApiService
import com.wkk.network.datasource.CourseRemoteDataSource
import com.wkk.network.model.NetworkCourse
import com.wkk.network.model.NetworkCourseChapter
import com.wkk.network.model.NetworkPageData
import com.wkk.network.model.NetworkResult
import javax.inject.Inject

class CourseRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : CourseRemoteDataSource {

    override suspend fun getCourseList(): NetworkResult<List<NetworkCourse>> {
        return apiService.getCourseList()
    }

    override suspend fun getCourseChapters(
        courseId: String,
        page: Int,
        pageSize: Int,
        isAsc: Boolean,
    ): NetworkResult<NetworkPageData<NetworkCourseChapter>> {
        return apiService.getCourseChapters(page, courseId, "1".takeIf { isAsc })
    }
}
