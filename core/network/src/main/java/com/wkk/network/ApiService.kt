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
package com.wkk.network

import com.wkk.network.model.NetworkArticle
import com.wkk.network.model.NetworkCourse
import com.wkk.network.model.NetworkPageData
import com.wkk.network.model.NetworkResult
import com.wkk.network.model.NetworkUser
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(UrlConstants.LOGIN)
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): NetworkResult<NetworkUser>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(UrlConstants.LOGIN)
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String,
    ): NetworkResult<NetworkUser>

    /**
     * 退出登录
     */
    @GET(UrlConstants.LOGOUT)
    suspend fun logout(): NetworkResult<Any?>

    /**
     * 未读消息
     */
    @GET("message/lg/count_unread/json")
    suspend fun unReadMessageCount(): NetworkResult<Any?>

    /**
     * 首页文章列表
     */
    @GET("/article/list/{page}/json")
    suspend fun fetchArticle(
        @Path("page") page: Int = 0,
        @Query("page_size") pageSize: Int = 20,
    ): NetworkResult<NetworkPageData<NetworkArticle>>

    @POST("/lg/collect/{articleId}/json")
    suspend fun collectArticle(@Path("articleId") articleId: String): NetworkResult<Any>

    @POST("/lg/uncollect_originId/{articleId}/json")
    suspend fun unCollectArticle(@Path("articleId") articleId: String): NetworkResult<Any>

    @GET("chapter/547/sublist/json")
    suspend fun getCourseList(): NetworkResult<List<NetworkCourse>>
}
