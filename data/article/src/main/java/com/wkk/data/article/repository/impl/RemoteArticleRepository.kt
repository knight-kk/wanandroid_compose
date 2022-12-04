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
package com.wkk.data.article.repository.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.room.withTransaction
import com.wkk.data.article.ArticleRemoteMediator
import com.wkk.data.repository.ArticleRepository
import com.wkk.database.AppDatabase
import com.wkk.database.dao.ArticleDao
import com.wkk.database.model.asExternalModule
import com.wkk.model.Article
import com.wkk.network.datasource.ArticleRemoteDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteArticleRepository @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleDao: ArticleDao,
    private val database: AppDatabase
) : ArticleRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagerFlow(pageSize: Int) = Pager(
        config = PagingConfig(10),
        remoteMediator = ArticleRemoteMediator(
            articleRemoteDataSource,
            database
        ),
        pagingSourceFactory = { articleDao.getArticles() }
    ).flow.map { pagingData -> pagingData.map { it.asExternalModule() } }

    override suspend fun toggleCollection(article: Article) = database.withTransaction {
        val articleEntity = articleDao.getArticle(article.id) ?: return@withTransaction false
        articleEntity.collect = !article.collect
        articleDao.update(articleEntity)
        // TODO 网络请求
        true

    }
}