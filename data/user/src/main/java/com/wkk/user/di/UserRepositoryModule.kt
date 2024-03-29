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
package com.wkk.user.di

import com.wkk.user.repository.HistoryRepository
import com.wkk.user.repository.UserRepository
import com.wkk.user.repository.impl.HistoryRepositoryImpl
import com.wkk.user.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserRepositoryModule {

    @Binds
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl,
    ): UserRepository

    @Binds
    fun bindsHistoryRepository(
        userRepository: HistoryRepositoryImpl,
    ): HistoryRepository
}
