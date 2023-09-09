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
package com.wkk.user.repository.impl

import androidx.paging.map
import com.wkk.network.datasource.CoinRemoteDataSource
import com.wkk.network.model.asExternalModule
import com.wkk.user.convert.asExternalModule
import com.wkk.user.repository.UserCoinRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserCoinRepositoryImpl @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
) : UserCoinRepository {
    override suspend fun fetchUserCoin() =
        coinRemoteDataSource.fetchUserCoin().asExternalModule { it.asExternalModule() }

    override fun fetchCoinRank() =
        coinRemoteDataSource.fetchCoinRank()
            .map { pagingData ->
                pagingData.map { it.asExternalModule() }
            }

    override fun fetchCoinRecord() = coinRemoteDataSource.fetchCoinRecord()
        .map { pagingData ->
            pagingData.map { it.asExternalModule() }
        }
}
