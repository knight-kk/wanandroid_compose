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
package com.wkk.user.screen

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkk.model.ArticleHistory
import com.wkk.user.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReadHistoryViewModel @Inject constructor(
    private val repository: HistoryRepository,
) : ViewModel() {

    private val articleHistoryDataState = MutableStateFlow(ArticleHistoryData(0, true, mapOf()))

    val uiDataState: StateFlow<ArticleHistoryData> =
        articleHistoryDataState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ArticleHistoryData(0, true, mapOf()),
        )

    suspend fun fetchData(data: ArticleHistoryData = articleHistoryDataState.value) {
        if (data.isDataEnd) return
        articleHistoryDataState.update { it.copy(isLoading = true) }
        val historyData = repository.getHistoryList(data.page, 10)
            .groupBy { DateFormat.format("yyyy-MM-dd", it.updateTime).toString() }
        articleHistoryDataState.value = data.merge(historyData)
    }

    suspend fun refresh() {
        fetchData(ArticleHistoryData(0, true, mapOf()))
    }
}

data class ArticleHistoryData(
    val page: Int,
    val isLoading: Boolean = true,
    val historyData: Map<String, List<ArticleHistory>>,
    val isDataEnd: Boolean = false,
) {
    fun merge(historyData: Map<String, List<ArticleHistory>>): ArticleHistoryData {
        if (historyData.isEmpty()) return copy(isDataEnd = true)
        val newHistoryData = this.historyData.toMutableMap()
        for ((key, list) in historyData) {
            val histories = newHistoryData[key]
            if (histories == null) {
                newHistoryData[key] = list
            } else {
                newHistoryData[key] = histories + list
            }
        }
        return ArticleHistoryData(page + 1, false, newHistoryData, newHistoryData.isEmpty())
    }
}
