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
package com.wkk.database.model

import androidx.core.text.HtmlCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wkk.database.utils.DateTimeUtils
import com.wkk.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "desc")
    val desc: String = "",
    @ColumnInfo(name = "link")
    val link: String = "",
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "collect")
    val collect: Boolean = false,
    @ColumnInfo(name = "publish_time")
    val publishTime: Long = -1L,
    @ColumnInfo(name = "type")
    val type: Int = -1,
    @ColumnInfo(name = "user_id")
    val userId: Long = -1L,
    @ColumnInfo(name = "chapter_id")
    val chapterId: Long = -1L,
    @ColumnInfo(name = "chapter_name")
    val chapterName: String = "",
    @ColumnInfo(name = "super_chapter_id")
    val superChapterId: Long = -1L,
    @ColumnInfo(name = "super_chapter_name")
    val superChapterName: String = "",
)

fun ArticleEntity.asExternalModule() = Article(
    id = id,
    title = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
    desc = desc,
    link = link,
    author = author,
    collect = collect,
    formatDateTime = DateTimeUtils.formatDate(publishTime),
    category = arrayOf(superChapterName, chapterName).filter { it.isNotEmpty() }.joinToString("/")
)
