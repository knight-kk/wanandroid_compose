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
package com.wkk.network.datasource

import com.wkk.network.model.NetworkResult
import com.wkk.network.model.NetworkUser

interface UserRemoteDataSource {
    suspend fun login(userName: String, password: String): NetworkResult<NetworkUser>

    suspend fun logout(): NetworkResult<Any?>

    suspend fun register(
        userName: String,
        password: String,
        rePassword: String
    ): NetworkResult<NetworkUser>
}
