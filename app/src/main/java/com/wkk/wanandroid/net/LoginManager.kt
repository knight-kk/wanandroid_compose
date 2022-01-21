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

import androidx.annotation.WorkerThread
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.wkk.wanandroid.App
import com.wkk.wanandroid.constants.DataStoreConstants
import com.wkk.wanandroid.constants.UrlConstants
import com.wkk.wanandroid.utils.ext.dataStore
import com.wkk.wanandroid.utils.ext.getValue
import com.wkk.wanandroid.utils.ext.remove
import com.wkk.wanandroid.utils.ext.setValue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

object LoginManager {
    private val cacheCookie = ConcurrentHashMap<String, List<Cookie>>()
    private val cookiesKey by lazy { stringSetPreferencesKey(DataStoreConstants.LOGIN_COOKIES_KEY) }

    var isLogin = false
        private set

    suspend fun login() {
        isLogin = true
        App.getInstance().dataStore.setValue(DataStoreConstants.IS_LOGIN_KEY, true)
    }

    suspend fun logout() {
        isLogin = false
        cacheCookie.clear()
        App.getInstance().dataStore.remove(booleanPreferencesKey(DataStoreConstants.IS_LOGIN_KEY))
        App.getInstance().dataStore.remove(stringSetPreferencesKey(DataStoreConstants.LOGIN_COOKIES_KEY))
    }

    @WorkerThread
    fun getCookies(url: HttpUrl) = runBlocking {
        if (isLogin.not()) return@runBlocking null
        val list = getCacheCookies(url)
        if (list.isNullOrEmpty()) {
            logout()
        }
        list
    }

    private suspend fun getCacheCookies(url: HttpUrl): List<Cookie>? {
        var list = cacheCookie[url.host]
        if (list.isNullOrEmpty()) {
            val cookieSet =
                App.getInstance().dataStore.getValue(cookiesKey).first() ?: return null
            list = cookieSet.mapNotNull { Cookie.parse(url, it) }.toList()
        }
        val currentTimeMillis = System.currentTimeMillis()
        return list.filter { cookie -> cookie.expiresAt > currentTimeMillis }
    }

    @WorkerThread
    fun saveCookie(url: HttpUrl, cookies: List<Cookie>) {
        val urlStr = url.toString()
        if (urlStr.contains(UrlConstants.LOGIN).not() &&
            urlStr.contains(UrlConstants.REGISTER).not() &&
            urlStr.contains(UrlConstants.LOGOUT).not()
        ) return
        cacheCookie[url.host] = cookies
        runBlocking {
            App.getInstance().dataStore.setValue(cookiesKey, cookies.map { it.toString() }.toSet())
        }
    }
}
