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
package com.wkk.data.article

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.wkk.data.article.model.asEntity
import com.wkk.database.AppDatabase
import com.wkk.database.model.ArticleEntity
import com.wkk.database.model.ArticleRemoteKey
import com.wkk.network.datasource.ArticleRemoteDataSource

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val database: AppDatabase
) : RemoteMediator<Int, ArticleEntity>() {

    private val startPage = 0
    private val articleDao = database.articleDao()
    private val articleRemoteKeysDao = database.articleKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        Log.i("ArticleRemoteMediator", "load: loadType : $loadType")
        val page: Int = when (loadType) {
            LoadType.REFRESH -> startPage
            LoadType.PREPEND ->
                return MediatorResult.Success(true)
            LoadType.APPEND -> {
                val remoteKey =
                    getRemoteKeyForLastItem(state) ?: return MediatorResult.Success(true)
                remoteKey.nextKey ?: startPage
            }
        }

        try {
            val networkResult = articleRemoteDataSource.fetchArticle(page, state.config.pageSize)
            if (networkResult.isSuccess().not()) {
                return MediatorResult.Success(true)
            }
            val datas = networkResult.data?.datas ?: emptyList()
            val endOfPaginationReached = datas.isEmpty()
            val nextKey = if (endOfPaginationReached) null else page + 1
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleRemoteKeysDao.clear()
                    articleDao.clear()
                }
                val keys = datas.map { ArticleRemoteKey(it.id, null, nextKey) }
                articleRemoteKeysDao.insertAll(keys)
                articleDao.insertAll(datas.map { it.asEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): ArticleRemoteKey? {
        val articleEntity = state.lastItemOrNull() ?: return null
        return articleRemoteKeysDao.remoteKeysRepoId(articleEntity.id)
    }
}
