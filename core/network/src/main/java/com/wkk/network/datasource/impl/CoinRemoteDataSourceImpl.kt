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

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wkk.network.ApiService
import com.wkk.network.datasource.CoinRemoteDataSource
import com.wkk.network.datasource.RemotePagingSource
import javax.inject.Inject

class CoinRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : CoinRemoteDataSource {
    override suspend fun fetchUserCoin() = apiService.fetchUserCoin()

    override fun fetchCoinRecord() = Pager(
        config = PagingConfig(20),
        initialKey = 1,
        pagingSourceFactory = {
            RemotePagingSource(apiService::fetchCoinRecords)
        },
    ).flow

    override fun fetchCoinRank() = Pager(
        config = PagingConfig(20),
        initialKey = 1,
        pagingSourceFactory = {
            RemotePagingSource(apiService::fetchCoinRank)
        },
    ).flow
}
