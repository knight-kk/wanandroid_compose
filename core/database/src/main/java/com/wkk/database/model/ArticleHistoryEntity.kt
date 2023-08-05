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
package com.wkk.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wkk.model.Article
import com.wkk.model.ArticleHistory

@Entity(
    tableName = "article_history",
)
data class ArticleHistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "article_id")
    val articleId: String,
    @ColumnInfo(name = "update_time")
    val updateTime: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "desc")
    val desc: String,
    @ColumnInfo(name = "link")
    val link: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "collect")
    var collect: Boolean,
    @ColumnInfo(name = "category")
    var category: String,
) {

    companion object {
        fun create(article: Article) = ArticleHistoryEntity(
            articleId = article.id,
            updateTime = System.currentTimeMillis(),
            title = article.title,
            desc = article.desc,
            link = article.link,
            author = article.author,
            collect = article.collect,
            category = article.category,
        )
    }
}

fun ArticleHistoryEntity.asExternalModule(): ArticleHistory {
    return ArticleHistory(
        id = articleId,
        title = title,
        desc = desc,
        link = link,
        author = author,
        collect = collect,
        updateTime = updateTime,
        category = category,
    )
}
