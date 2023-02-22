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
package com.wkk.data.course.repository.impl

import com.wkk.data.course.model.asExternalModule
import com.wkk.data.course.repository.CourseRepository
import com.wkk.model.Course
import com.wkk.network.datasource.CourseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseRepositoryImpl
@Inject constructor(
    private val remoteDataSource: CourseRemoteDataSource,
) : CourseRepository {

    override fun getCourseList(): Flow<List<Course>> = flow<List<Course>> {
        val result = remoteDataSource.getCourseList()
        if (result.isSuccess()) {
            emit(result.data?.map { it.asExternalModule() } ?: emptyList())
        }
        throw RuntimeException(result.errorMsg)
    }
}
