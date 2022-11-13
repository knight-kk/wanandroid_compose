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
package com.wkk.model

import com.squareup.moshi.Json

data class KnowledgeTree(
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "children")
    val tags: List<Tag> = emptyList(),
    @Json(name = "type")
    val type: Int = 0,
) {

    data class Tag(
        @Json(name = "id")
        val id: Long = 0,
        @Json(name = "name")
        val name: String = "",
        @Json(name = "courseId")
        val courseId: Int = 0,
        @Json(name = "cover")
        val cover: String = "",
        @Json(name = "desc")
        val desc: String = "",
    )
}
