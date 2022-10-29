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
package com.wkk.wanandroid.data.impl

import com.wkk.wanandroid.data.KnowledgeTreeRepository
import com.wkk.wanandroid.net.ApiService
import javax.inject.Inject

class RemoteKnowledgeTreeRepository @Inject constructor(private val apiService: ApiService) : KnowledgeTreeRepository {
    override suspend fun getKnowledgeTrees() = apiService.getKnowledgeTrees()
}