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

import com.example.datastore.datasource.PreferencesDataSource
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class LoginCookieJar(
    private val preferencesDataSource: PreferencesDataSource
) : CookieJar {

    private val cacheCookie = ConcurrentHashMap<String, List<Cookie>>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        var list = cacheCookie[url.host]
        if (list.isNullOrEmpty()) {
            val cookieSet = runBlocking { preferencesDataSource.getCookies().first() }
            list = cookieSet.mapNotNull { Cookie.parse(url, it) }.toList()
        }
        return list.filter { cookie -> cookie.expiresAt > System.currentTimeMillis() }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val urlStr = url.toString()
        val needSaveCookieUrls =
            listOf(UrlConstants.LOGIN, UrlConstants.REGISTER, UrlConstants.LOGOUT)
        if (needSaveCookieUrls.find { urlStr.contains(it) } == null) return
        cacheCookie[url.host] = cookies
        runBlocking { preferencesDataSource.saveCookies(cookies.map { it.toString() }.toSet()) }
    }
}
