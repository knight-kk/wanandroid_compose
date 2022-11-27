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
import com.wkk.data.article.model.asEntity
import com.wkk.database.dao.ArticleDao
import com.wkk.database.dao.ArticleRemoteKeysDao
import com.wkk.database.model.ArticleEntity
import com.wkk.database.model.ArticleRemoteKey
import com.wkk.network.datasource.ArticleRemoteDataSource

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleDao: ArticleDao,
    private val articleRemoteKeysDao: ArticleRemoteKeysDao,
) : RemoteMediator<Int, ArticleEntity>() {

    private val startPage = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        Log.i("ArticleRemoteMediator", "load: loadType : $loadType")
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: startPage
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(remoteKey != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(remoteKey != null)
                nextKey
            }
        }

        Log.i("ArticleRemoteMediator", "load: page : $page")

        try {
            val networkResult = articleRemoteDataSource.fetchArticle(page, state.config.pageSize)
            if (networkResult.isSuccess().not()) {
                return MediatorResult.Success(true)
            }
            val datas = networkResult.data?.datas
            if (datas.isNullOrEmpty()) {
                return MediatorResult.Success(true)
            }
            if (loadType == LoadType.REFRESH) {
                articleRemoteKeysDao.clear()
                articleDao.clear()
            }

            val prevKey = if (page == startPage) null else page - 1
            val nextKey = page + 1

            val keys = datas.map { ArticleRemoteKey(it.id, prevKey, nextKey) }
            articleRemoteKeysDao.insertAll(keys)
            articleDao.insertAll(datas.map { it.asEntity() })
            return MediatorResult.Success(false)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): ArticleRemoteKey? {
        val articleEntityPage = state.pages.firstOrNull { it.data.isNotEmpty() } ?: return null
        val articleEntity = articleEntityPage.data.firstOrNull() ?: return null
        return articleRemoteKeysDao.remoteKeysRepoId(articleEntity.id)
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): ArticleRemoteKey? {
        val articleEntityPage = state.pages.lastOrNull { it.data.isNotEmpty() } ?: return null
        val articleEntity = articleEntityPage.data.lastOrNull() ?: return null
        return articleRemoteKeysDao.remoteKeysRepoId(articleEntity.id)
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleEntity>): ArticleRemoteKey? {
        val position = state.anchorPosition ?: return null
        val articleId = state.closestItemToPosition(position)?.id ?: return null
        return articleRemoteKeysDao.remoteKeysRepoId(articleId)
    }
}
