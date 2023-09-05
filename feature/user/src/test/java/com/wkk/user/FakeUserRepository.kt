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
package com.wkk.user

import com.wkk.model.DataResult
import com.wkk.model.User
import com.wkk.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

object TestUser {
    const val userName = "123@gamil.com"
    const val password = "123456"
}

class FakeUserRepository : UserRepository {

    override suspend fun login(userName: String, password: String): DataResult<Any> {
        if (TestUser.userName == userName && TestUser.password == password) {
            return DataResult.Success(Any())
        }
        return DataResult.Error("账号密码不匹配")
    }

    override fun getUserInfo(): Flow<User> {
        TODO()
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserInfo(): DataResult<Unit> {
        TODO()
    }
}
