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
package com.wkk.wanandroid.net

import com.wkk.wanandroid.constants.UrlConstants
import com.wkk.wanandroid.model.Result
import com.wkk.wanandroid.model.UserDetail
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(UrlConstants.LOGIN)
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String
    ): Result<UserDetail>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(UrlConstants.LOGIN)
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    ): Result<UserDetail>

    /**
     * 退出登录
     */
    @GET(UrlConstants.LOGOUT)
    suspend fun logout(): Result<Any?>

    /**
     * 未读消息
     */
    @GET("message/lg/count_unread/json")
    suspend fun unReadMessageCount(): Result<Any?>
}
