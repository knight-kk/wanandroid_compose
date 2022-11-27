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
package com.wkk.network.datasource.impl

import com.wkk.model.Article
import com.wkk.network.ApiService
import com.wkk.network.datasource.ArticleRemoteDataSource
import com.wkk.network.model.NetworkArticle
import com.wkk.network.model.NetworkPageData
import com.wkk.network.model.NetworkResult
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : ArticleRemoteDataSource {
    override suspend fun fetchArticle(page: Int, pageSize: Int): NetworkResult<NetworkPageData<NetworkArticle>> {
        return apiService.fetchArticle(page, pageSize)
    }

    override suspend fun toggleCollection(article: Article): NetworkResult<Any> {
        return if (article.collect) {
            apiService.unCollectArticle(article.id)
        } else {
            apiService.collectArticle(article.id)
        }
    }
}
