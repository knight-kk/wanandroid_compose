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
package com.wkk.database.di

import com.wkk.database.AppDatabase
import com.wkk.database.dao.ArticleDao
import com.wkk.database.dao.ArticleHistoryDao
import com.wkk.database.dao.ArticleRemoteKeysDao
import com.wkk.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesAuthorDao(
        database: AppDatabase,
    ): ArticleDao = database.articleDao()

    @Provides
    fun providesArticleRemoteKeysDao(
        database: AppDatabase,
    ): ArticleRemoteKeysDao = database.articleKeyDao()

    @Provides
    fun providesUserDao(
        database: AppDatabase,
    ): UserDao = database.userDao()

    @Provides
    fun providesArticleHistoryDao(
        database: AppDatabase,
    ): ArticleHistoryDao = database.articleHistoryDao()
}
