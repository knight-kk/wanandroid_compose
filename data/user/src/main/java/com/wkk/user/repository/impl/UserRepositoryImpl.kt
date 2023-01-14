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
package com.wkk.user.repository.impl

import com.wkk.database.dao.UserDao
import com.wkk.database.model.asExternalModule
import com.wkk.datastore.datasource.PreferencesDataSource
import com.wkk.model.DataResult
import com.wkk.model.User
import com.wkk.network.datasource.UserRemoteDataSource
import com.wkk.user.model.asEntity
import com.wkk.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun login(userName: String, password: String): DataResult<Any> {
        val result = userRemoteDataSource.login(userName, password)
        if (result.isSuccess().not()) return DataResult.Error(result.errorMsg)
        val networkUser = result.data ?: return DataResult.Error("获取数据失败")
        userDao.insertOrUpdate(networkUser.asEntity())
        preferencesDataSource.saveLoginInfo(networkUser.id)
        return DataResult.Success(null)
    }

    override suspend fun logout() {
        preferencesDataSource.clearLoginInfo()
        userRemoteDataSource.logout()
    }

    override fun getUserInfo(): Flow<User> {
        return preferencesDataSource.getUserId()
            .mapNotNull { userId ->
                if (userId.isEmpty()) {
                    throw RuntimeException("未登录")
                }
                userDao.getUser(userId)?.asExternalModule()
            }
    }
}
