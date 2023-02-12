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

import com.wkk.datastore.datasource.PreferencesDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class LoginCookieJar(
    private val preferencesDataSource: PreferencesDataSource,
) : CookieJar {

    private var cacheCookies: List<Cookie>? = null

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val list = (
            cacheCookies ?: runBlocking {
                preferencesDataSource.getCookies().first().mapNotNull { Cookie.parse(url, it) }
                    .toList()
            }.also { cacheCookies = it }
            )
        if (list.isEmpty()) return list
        val filterList = list.filter { cookie -> cookie.expiresAt > System.currentTimeMillis() }
        if (filterList.isEmpty()) {
            cacheCookies = null
            runBlocking { preferencesDataSource.clearLoginInfo() }
        }
        return filterList
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (isNeedSaveCookie(url.toString()).not()) return
        cacheCookies = cookies
        runBlocking { preferencesDataSource.saveCookies(cookies.map { it.toString() }.toSet()) }
    }

    private fun isNeedSaveCookie(url: String): Boolean {
        if (url.contains(UrlConstants.LOGIN)) return true
        if (url.contains(UrlConstants.REGISTER)) return true
        if (url.contains(UrlConstants.LOGOUT)) return true
        return false
    }
}
