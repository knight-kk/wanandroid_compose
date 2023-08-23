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
package com.wkk.network.model

import androidx.core.text.HtmlCompat
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.wkk.model.Article
import com.wkk.utils.DateTimeUtils

@JsonClass(generateAdapter = true)
data class NetworkArticle(
    @Json(name = "apkLink") val apkLink: String = "",
    @Json(name = "audit") val audit: Int = -1,
    @Json(name = "author") val author: String = "",
    @Json(name = "canEdit") val canEdit: Boolean = false,
    @Json(name = "chapterId") val chapterId: Int = -1,
    @Json(name = "chapterName") val chapterName: String = "",
    @Json(name = "collect") var collect: Boolean = false,
    @Json(name = "courseId") val courseId: Int = 0,
    @Json(name = "desc") val desc: String = "",
    @Json(name = "descMd") val descMd: String = "",
    @Json(name = "envelopePic") val envelopePic: String = "",
    @Json(name = "fresh") val fresh: Boolean = false,
    @Json(name = "host") val host: String = "",
    @Json(name = "id") val id: String = "",
    @Json(name = "link") val link: String = "",
    @Json(name = "niceDate") val niceDate: String = "",
    @Json(name = "niceShareDate") val niceShareDate: String = "",
    @Json(name = "origin") val origin: String = "",
    @Json(name = "prefix") val prefix: String = "",
    @Json(name = "projectLink") val projectLink: String = "",
    @Json(name = "publishTime") val publishTime: Long = 1L,
    @Json(name = "realSuperChapterId") val realSuperChapterId: Int = 1,
    @Json(name = "selfVisible") val selfVisible: Int = 1,
    @Json(name = "shareDate") val shareDate: Long = 1L,
    @Json(name = "shareUser") val shareUser: String = "",
    @Json(name = "superChapterId") val superChapterId: Int = 0,
    @Json(name = "superChapterName") val superChapterName: String = "",
    @Json(name = "title") val title: String = "",
    @Json(name = "type") val type: Int = 1,
    @Json(name = "userId") val userId: Int = 1,
    @Json(name = "visible") val visible: Int = 0,
    @Json(name = "zan") val zan: Int = 0,
)

fun NetworkArticle.asExternalModule() = Article(
    id = id,
    title = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
    desc = desc,
    link = link,
    author = author,
    collect = collect,
    formatDateTime = DateTimeUtils.formatDate(publishTime),
    category = arrayOf(superChapterName, chapterName).filter { it.isNotEmpty() }.joinToString("/"),
)
