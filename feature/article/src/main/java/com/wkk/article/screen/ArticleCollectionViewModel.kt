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
package com.wkk.article.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkk.data.article.repository.ArticleRepository
import com.wkk.model.Article
import com.wkk.model.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArticleCollectionViewModel @Inject constructor(
    private val repository: ArticleRepository,
) : ViewModel() {

    private val articleCollectionDataState = MutableStateFlow(ArticleCollectionData())

    val uiDataState: StateFlow<ArticleCollectionData> =
        articleCollectionDataState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ArticleCollectionData(listOf()),
        )

    suspend fun fetchData() {
        val (_, page) = articleCollectionDataState.value
        val articles = when (val result = repository.getCollections(page, 20)) {
            is DataResult.Error -> null
            is DataResult.Success -> result.data?.datas
        }
        articleCollectionDataState.update { it.merge(articles) }
    }

    suspend fun toggleCollection(article: Article) =
        repository.toggleCollection(article)
}

data class ArticleCollectionData(
    val articles: List<Article> = listOf(),
    val page: Int = 0,
    val isLoading: Boolean = true,
    val isDataEnd: Boolean = false,
) {
    fun merge(newArticles: List<Article>?): ArticleCollectionData {
        if (newArticles.isNullOrEmpty()) return copy(isLoading = false, isDataEnd = true)
        return ArticleCollectionData(
            articles = articles + newArticles,
            page = page + 1,
            isLoading = false,
            isDataEnd = false,
        )
    }
}
