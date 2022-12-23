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

@JsonClass(generateAdapter = true)
data class NetworkUser(
    @Json(name = "id")
    val id: String,
    @Json(name = "coinCount")
    val coinCount: Int = 0,
    @Json(name = "email")
    val email: String = "",
    @Json(name = "icon")
    val icon: String = "",
    @Json(name = "nickname")
    val nickname: String = "",
    @Json(name = "publicName")
    val publicName: String = "",
    @Json(name = "type")
    val type: Int = 0,
    @Json(name = "username")
    val username: String = ""
)
