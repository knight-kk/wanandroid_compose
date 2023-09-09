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

import com.wkk.model.CoinInfo
import com.wkk.model.CoinRecord
import com.wkk.model.DataResult
import com.wkk.model.PageData
import com.wkk.network.ApiService
import com.wkk.network.model.asExternalModule
import com.wkk.network.model.asExternalWrapperPageDataModule
import com.wkk.user.convert.asExternalModule
import com.wkk.user.repository.UserCoinRepository
import javax.inject.Inject

class UserCoinRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserCoinRepository {
    override suspend fun fetchUserCoin(): DataResult<CoinInfo> {
        return apiService.fetchUserCoin().asExternalModule { it.asExternalModule() }
    }

    override suspend fun fetchCoinRank(page: Int): DataResult<PageData<CoinInfo>> {
        return apiService.fetchCoinRank(page).asExternalWrapperPageDataModule { it.asExternalModule() }
    }

    override suspend fun fetchCoinRecords(page: Int): DataResult<PageData<CoinRecord>> {
        return apiService.fetchCoinRecords(page).asExternalWrapperPageDataModule { it.asExternalModule() }
    }
}
