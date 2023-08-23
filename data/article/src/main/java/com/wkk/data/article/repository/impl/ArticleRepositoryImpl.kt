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
import com.wkk.data.article.repository.ArticleRepository
import com.wkk.database.AppDatabase
import com.wkk.database.dao.ArticleDao
import com.wkk.database.dao.ArticleHistoryDao
import com.wkk.database.model.ArticleHistoryEntity
import com.wkk.database.model.asExternalModule
import com.wkk.model.Article
import com.wkk.model.DataResult
import com.wkk.model.PageData
import com.wkk.network.datasource.ArticleRemoteDataSource
import com.wkk.network.model.NetworkArticle
import com.wkk.network.model.NetworkPageData
import com.wkk.network.model.asExternalModule
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleDao: ArticleDao,
    private val database: AppDatabase,
    private val articleHistoryDao: ArticleHistoryDao,
) : ArticleRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagerFlow(pageSize: Int) = Pager(
        config = PagingConfig(10),
        remoteMediator = ArticleRemoteMediator(
            articleRemoteDataSource,
            database,
        ),
        pagingSourceFactory = { articleDao.getArticles() },
    ).flow.map { pagingData -> pagingData.map { it.asExternalModule() } }

    override suspend fun toggleCollection(article: Article): DataResult<Any> =
        database.withTransaction {
            val articleEntity =
                articleDao.getArticle(article.id)
                    ?: return@withTransaction DataResult.Error("更新失败")
            articleEntity.collect = !article.collect
            articleDao.update(articleEntity)
            val result = articleRemoteDataSource.toggleCollection(article)
            if (result.isSuccess().not()) {
                articleEntity.collect = article.collect
                articleDao.update(articleEntity)
                return@withTransaction DataResult.Error(result.errorMsg, result.errorCode)
            }
            DataResult.Success()
        }

    override suspend fun getCollections(
        page: Int,
        pageSize: Int,
    ): DataResult<PageData<Article>> = runCatching {
        val networkResult = articleRemoteDataSource.fetchCollections(page, pageSize)
        val asArticle = fun NetworkArticle.() = asExternalModule()
        val asNetworkPageData = fun NetworkPageData<NetworkArticle>.() = asExternalModule(asArticle)
        return networkResult.asExternalModule(asNetworkPageData)
    }.getOrElse {
        DataResult.Error(it)
    }

    override suspend fun readArticle(article: Article) {
        articleHistoryDao.insert(ArticleHistoryEntity.create(article))
    }
}
