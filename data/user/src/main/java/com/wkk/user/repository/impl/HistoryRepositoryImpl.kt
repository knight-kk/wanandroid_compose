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
package com.wkk.user.repository.impl

import com.wkk.database.dao.ArticleHistoryDao
import com.wkk.database.model.asExternalModule
import com.wkk.user.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val articleHistoryDao: ArticleHistoryDao,
) : HistoryRepository {

    override suspend fun getHistoryList(page: Int, pageSize: Int) =
        articleHistoryDao.getList(page, pageSize).map { it.asExternalModule() }
}