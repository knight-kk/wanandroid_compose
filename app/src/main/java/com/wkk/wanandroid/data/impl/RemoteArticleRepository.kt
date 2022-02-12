package com.wkk.wanandroid.data.impl

import com.wkk.wanandroid.data.ArticleRepository
import com.wkk.wanandroid.model.Article
import com.wkk.wanandroid.model.PageData
import com.wkk.wanandroid.model.Result
import com.wkk.wanandroid.net.NetManager

class RemoteArticleRepository : ArticleRepository {

    override suspend fun getArticleList(page: Int): Result<PageData<Article>> {
        return NetManager.apiService.fetchArticle()
    }
}
