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
package com.wkk.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wkk.database.dao.ArticleDao
import com.wkk.database.dao.ArticleHistoryDao
import com.wkk.database.dao.ArticleRemoteKeysDao
import com.wkk.database.dao.UserDao
import com.wkk.database.model.ArticleEntity
import com.wkk.database.model.ArticleHistoryEntity
import com.wkk.database.model.ArticleRemoteKey
import com.wkk.database.model.UserEntity

@Database(
    entities = [
        ArticleEntity::class,
        ArticleRemoteKey::class,
        UserEntity::class,
        ArticleHistoryEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    abstract fun articleKeyDao(): ArticleRemoteKeysDao

    abstract fun userDao(): UserDao

    abstract fun articleHistoryDao(): ArticleHistoryDao
}
