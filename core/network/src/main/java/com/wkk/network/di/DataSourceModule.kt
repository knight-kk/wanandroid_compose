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
package com.wkk.network.di

import com.wkk.network.datasource.ArticleRemoteDataSource
import com.wkk.network.datasource.CoinRemoteDataSource
import com.wkk.network.datasource.CourseRemoteDataSource
import com.wkk.network.datasource.UserRemoteDataSource
import com.wkk.network.datasource.impl.ArticleRemoteDataSourceImpl
import com.wkk.network.datasource.impl.CoinRemoteDataSourceImpl
import com.wkk.network.datasource.impl.CourseRemoteDataSourceImpl
import com.wkk.network.datasource.impl.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsArticleRemoteDataSource(
        articleRemoteDataSource: ArticleRemoteDataSourceImpl,
    ): ArticleRemoteDataSource

    @Binds
    fun bindsUserRemoteDataSource(
        userRemoteDataSource: UserRemoteDataSourceImpl,
    ): UserRemoteDataSource

    @Binds
    fun bindsCourseRemoteDataSource(
        courseRemoteDataSource: CourseRemoteDataSourceImpl,
    ): CourseRemoteDataSource

    @Binds
    fun bindsCoinRemoteDataSource(
        coinRemoteDataSource: CoinRemoteDataSourceImpl,
    ): CoinRemoteDataSource
}
