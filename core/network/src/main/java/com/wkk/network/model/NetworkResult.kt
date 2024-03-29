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
import com.wkk.model.DataResult

@JsonClass(generateAdapter = true)
class NetworkResult<T>(
    @Json(name = "data")
    val data: T?,
    /**
     * errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
     * errorCode = -1001 代表登录失效，需要重新登录。
     */
    @Json(name = "errorCode")
    val errorCode: Int,
    @Json(name = "errorMsg")
    val errorMsg: String = "",
) {
    fun isSuccess() = errorCode == 0

    companion object {
        val asExternalModule = fun NetworkResult<*>.() = asExternalModule()
    }
}

fun <T> NetworkResult<T>.asExternalModule(): DataResult<T> {
    if (isSuccess()) {
        return DataResult.Success(data)
    }
    return DataResult.Error(errorMsg, errorCode)
}

inline fun <NetData, Data> NetworkResult<NetData>.asExternalModule(getData: (NetData) -> Data): DataResult<Data> {
    if (isSuccess()) {
        return DataResult.Success(if (data != null) getData(data) else null)
    }
    return DataResult.Error(errorMsg, errorCode)
}
