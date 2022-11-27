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

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *  分页数据
 *  ```json
 *      {
 *        "curPage": 1,
 *        "datas": [],
 *        "offset": 0,
 *        "over": false,
 *        "pageCount": 591,
 *        "size": 20,
 *        "total": 11819
 *        }
 *  ```
 *
 */
@JsonClass(generateAdapter = true)
data class NetworkPageData<T>(
    @Json(name = "curPage")
    val curPage: Int = 1,
    @Json(name = "datas")
    val datas: List<T> = emptyList(),
    @Json(name = "offset")
    val offset: Int = 0,
    /**
     * 是否是最后一页
     */
    @Json(name = "over")
    val over: Boolean = false,
    /**
     * 每页的数量
     */
    @Json(name = "size")
    val size: Int = 0,
    /**
     * 总页数
     */
    @Json(name = "pageCount")
    val pageCount: Int = 0,
    /**
     * 总条目
     */
    @Json(name = "total")
    val total: Int = 0,

)
