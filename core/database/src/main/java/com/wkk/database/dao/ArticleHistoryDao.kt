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
package com.wkk.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.wkk.database.model.ArticleHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleHistoryDao {

    @Query("SELECT * from article_history ORDER BY update_time desc LIMIT (:page * :pageSize), :pageSize")
    suspend fun getList(page: Int, pageSize: Int): List<ArticleHistoryEntity>

    @Query("SELECT strftime('%Y-%m-%d %H-%M-%S',update_time/1000,'unixepoch') as time from article_history group by time  ORDER BY update_time desc")
    fun getDateSection(): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleHistoryEntity: ArticleHistoryEntity)

    @Transaction
    @Query("DELETE FROM `article_history` WHERE article_id =:id ")
    suspend fun delete(id: String)

    @Query("DELETE from article_history")
    suspend fun clear()
}
