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
package com.wkk.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wkk.data.repository.ArticlePagingSource
import com.wkk.data.repository.ArticleRepository
import com.wkk.model.Article
import com.wkk.network.datasource.ArticleRemoteDataSource
import javax.inject.Inject

class RemoteArticleRepository @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override fun getPagerFlow(pageSize: Int) = Pager(PagingConfig(10)) {
        ArticlePagingSource(articleRemoteDataSource)
    }.flow

    override suspend fun toggleCollection(article: Article) =
        articleRemoteDataSource.toggleCollection(article)
}
