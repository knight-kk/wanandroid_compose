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
package com.wkk.wanandroid.utils.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")

fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>) =
    data.map { preferences -> preferences[key] }

fun DataStore<Preferences>.getBooleanValue(keyName: String) = getValue(booleanPreferencesKey(keyName))

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    edit { preferences -> preferences[key] = value }
}

suspend fun <T> DataStore<Preferences>.remove(key: Preferences.Key<T>) {
    edit { preferences -> preferences.remove(key) }
}

suspend fun DataStore<Preferences>.setValue(key: String, value: Any) {
    edit { preferences ->
        when (value) {
            is Boolean -> preferences[booleanPreferencesKey(key)] = value
            is Int -> preferences[intPreferencesKey(key)] = value
            is Double -> preferences[doublePreferencesKey(key)] = value
            is Long -> preferences[longPreferencesKey(key)] = value
            is Set<*> -> {
                val componentType = value::class.java.componentType
                @Suppress("UNCHECKED_CAST")
                if (componentType == String::class.java) {
                    preferences[stringSetPreferencesKey(key)] = value as Set<String>
                }
            }
        }
    }
}
