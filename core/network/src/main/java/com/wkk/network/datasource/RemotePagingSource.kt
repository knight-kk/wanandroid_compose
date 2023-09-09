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
package com.wkk.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wkk.network.model.NetworkPageData
import com.wkk.network.model.NetworkResult

class RemotePagingSource<T : Any>(
    val fetchData: suspend (page: Int) -> NetworkResult<NetworkPageData<T>>,
    val startPage: Int = 1,
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: startPage
            val result = fetchData(page)
            val pageData = result.data
            if (result.isSuccess().not() || pageData == null || pageData.datas.isEmpty()) {
                return LoadResult.Error(RuntimeException("没有更多数据"))
            }
            LoadResult.Page(
                data = pageData.datas,
                prevKey = null,
                nextKey = pageData.curPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
