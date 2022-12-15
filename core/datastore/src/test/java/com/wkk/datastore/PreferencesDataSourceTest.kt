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
package com.wkk.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.wkk.datastore.datasource.PreferencesDataSource
import com.wkk.datastore.datasource.impl.PreferencesDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class PreferencesDataSourceTest {

    private lateinit var preferencesDataSource: PreferencesDataSource
    private lateinit var preferencesCacheFile: File

    @Before
    fun createPreferencesDataSource() {
        preferencesCacheFile = File("test${Math.random()}.preferences_pb")
        preferencesDataSource = PreferencesDataSourceImpl(
            PreferenceDataStoreFactory.create {
                preferencesCacheFile
            }
        )
    }

    @After
    fun deletePreferencesCacheFile() {
        preferencesCacheFile.delete()
    }

    @Test
    fun isLoginIsFalseWhenDefault() = runTest {
        Assert.assertFalse(preferencesDataSource.isLogin().first())
    }

    @Test
    fun isLoginIsTrueWhenSaveLoginInfoNotNull() = runTest {
        preferencesDataSource.saveLoginInfo("001")
        Assert.assertTrue(preferencesDataSource.isLogin().first())
    }

    @Test
    fun `userId is not null when saveLoginInfo is not null`() = runTest {
        preferencesDataSource.saveLoginInfo("001")
        Assert.assertEquals("001", preferencesDataSource.getUserId().first())
    }
}
