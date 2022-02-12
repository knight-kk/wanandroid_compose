package com.wkk.wanandroid.data

import com.wkk.wanandroid.model.Article
import com.wkk.wanandroid.model.PageData
import com.wkk.wanandroid.model.Result

interface ArticleRepository {

    suspend fun getArticleList(page: Int): Result<PageData<Article>>
}
