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
package com.wkk.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wkk.model.Article
import com.wkk.network.datasource.ArticleRemoteDataSource

class ArticlePagingSource(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        Log.i(TAG, "getRefreshKey: " + state.anchorPosition)
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        Log.i(TAG, "load: " + params.key)
        return try {
            val result = articleRemoteDataSource.fetchArticle(params.key ?: 0, params.loadSize)
            val pageData = result.data
            if (!result.isSuccess() || pageData == null) {
                return LoadResult.Error(RuntimeException("数据获取失败"))
            }
            return LoadResult.Page(
                data = pageData.datas,
                prevKey = null,
                nextKey = if (pageData.over) null else pageData.curPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "ArticlePagingSource"
    }
}
