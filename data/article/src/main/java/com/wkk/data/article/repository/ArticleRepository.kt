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
package com.wkk.data.article.repository

import androidx.paging.PagingData
import com.wkk.model.Article
import com.wkk.model.DataResult
import com.wkk.model.PageData
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getPagerFlow(pageSize: Int = 20): Flow<PagingData<Article>>
    suspend fun toggleCollection(article: Article): DataResult<Any>

    suspend fun getCollections(page: Int, pageSize: Int): DataResult<PageData<Article>>

    suspend fun readArticle(article: Article)
}
