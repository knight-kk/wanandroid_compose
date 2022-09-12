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
package com.wkk.wanandroid.utils

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.wkk.wanandroid.App
import com.wkk.wanandroid.constants.DataStoreConstants
import com.wkk.wanandroid.constants.UrlConstants
import com.wkk.wanandroid.dao.AppDatabase
import com.wkk.wanandroid.model.User
import com.wkk.wanandroid.utils.ext.dataStore
import com.wkk.wanandroid.utils.ext.getValue
import com.wkk.wanandroid.utils.ext.remove
import com.wkk.wanandroid.utils.ext.setValue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

object UserManager {
    private val cacheCookie = ConcurrentHashMap<String, List<Cookie>>()
    private val cookiesKey by lazy { stringSetPreferencesKey(DataStoreConstants.LOGIN_COOKIES_KEY) }

    var isLogin: Boolean = false

    suspend fun observeLoginStatus() {
        App.getInstance().dataStore.data
            .map { it[booleanPreferencesKey(DataStoreConstants.IS_LOGIN_KEY)] }
            .collect { isLogin ->
                Log.i("TAG", "observeLoginStatus: " + isLogin)
                this.isLogin = isLogin ?: false
            }
    }

    fun getUserId(): Long? = runBlocking {
        App.getInstance().dataStore.getValue(longPreferencesKey(DataStoreConstants.USER_ID)).first()
    }

    suspend fun getLoginUser(): User? {
        val userId = getUserId() ?: return null
        return getUser(userId)
    }

    suspend fun getUser(userId: Long): User =
        AppDatabase.getInstance().userDao().getUserById(userId)

    suspend fun login(user: User) {
        AppDatabase.getInstance().userDao().insert(user)
        App.getInstance().dataStore.apply {
            setValue(DataStoreConstants.USER_ID, user.id)
            setValue(DataStoreConstants.IS_LOGIN_KEY, true)
        }
    }

    suspend fun logout() {
        cacheCookie.clear()
        App.getInstance().dataStore.apply {
            setValue(DataStoreConstants.IS_LOGIN_KEY, false)
            remove(stringSetPreferencesKey(DataStoreConstants.USER_ID))
        }
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
