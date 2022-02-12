package com.wkk.wanandroid.model

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
data class PageData<T>(
    @Json(name = "curPage")
    val curPage: Int,
    @Json(name = "datas")
    val datas: List<T>,
    @Json(name = "offset")
    val offset: Int,
    /**
     * 是否是最后一页
     */
    @Json(name = "over")
    val over: Int,
    /**
     * 每页的数量
     */
    @Json(name = "size")
    val size: Int,
    /**
     * 总页数
     */
    @Json(name = "pageCount")
    val pageCount: Int,
    /**
     * 总条目
     */
    @Json(name = "total")
    val total: Int,

)
