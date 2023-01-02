/*
 * Copyright 2023 knight-kk
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
package com.wkk.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wkk.database.dao.UserDao
import com.wkk.database.model.UserEntity
import com.wkk.database.model.asExternalModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_insert_user() = runTest {
        val user = UserEntity("1", email = "xxx@gmail.com", username = "kk")
        userDao.insertOrUpdate(user)
        val queryUser = userDao.getUser(user.id).first()
        assertEquals(user.id, queryUser?.id)
    }

    @Test
    fun test_delete_user() = runTest {
        val user = UserEntity("1", email = "xxx@gmail.com", username = "kk")
        userDao.insertOrUpdate(user)
        val userFlow = userDao.getUser(user.id)
        assertNotNull(userFlow.first())
        userDao.delete(user.asExternalModule().id)
        assertEquals(userFlow.first(), null)
    }
}
