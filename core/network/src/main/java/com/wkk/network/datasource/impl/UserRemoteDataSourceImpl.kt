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
package com.wkk.network.datasource.impl

import com.wkk.network.ApiService
import com.wkk.network.datasource.UserRemoteDataSource
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : UserRemoteDataSource {
    override suspend fun login(userName: String, password: String) =
        apiService.login(userName, password)

    override suspend fun fetchUserInfo() = apiService.fetchUserInfo()

    override suspend fun logout() = apiService.logout()

    override suspend fun register(userName: String, password: String, rePassword: String) =
        apiService.register(userName, password, rePassword)
}
