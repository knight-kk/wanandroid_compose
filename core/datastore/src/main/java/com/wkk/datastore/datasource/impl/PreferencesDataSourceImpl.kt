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
package com.wkk.datastore.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.wkk.datastore.DataStoreConstants
import com.wkk.datastore.datasource.PreferencesDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : PreferencesDataSource {

    override fun isLogin() =
        dataStore.data.map { it[booleanPreferencesKey(DataStoreConstants.IS_LOGIN_KEY)] ?: false }

    override suspend fun saveLoginInfo(userId: String) {
        if (userId.isEmpty()) return
        dataStore.edit {
            it[booleanPreferencesKey(DataStoreConstants.IS_LOGIN_KEY)] = true
            it[stringPreferencesKey(DataStoreConstants.USER_ID)] = userId
        }
    }

    override suspend fun clearLoginInfo() {
        dataStore.edit {
            it[booleanPreferencesKey(DataStoreConstants.IS_LOGIN_KEY)] = false
            it[stringPreferencesKey(DataStoreConstants.USER_ID)] = ""
            it[stringSetPreferencesKey(DataStoreConstants.LOGIN_COOKIES_KEY)] = emptySet()
        }
    }

    override fun getUserId() =
        dataStore.data.map { it[stringPreferencesKey(DataStoreConstants.USER_ID)] ?: "" }

    override suspend fun getCookies() =
        dataStore.data.map {
            it[stringSetPreferencesKey(DataStoreConstants.LOGIN_COOKIES_KEY)] ?: emptySet()
        }

    override suspend fun saveCookies(cookies: Set<String>) {
        dataStore.edit {
            it[stringSetPreferencesKey(DataStoreConstants.LOGIN_COOKIES_KEY)] = cookies
        }
    }
}
