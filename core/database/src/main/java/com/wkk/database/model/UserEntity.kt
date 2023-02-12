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
package com.wkk.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wkk.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "coinCount")
    val coinCount: Int = 0,
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "icon")
    val icon: String = "",
    @ColumnInfo(name = "nickname")
    val nickname: String = "",
    @ColumnInfo(name = "publicName")
    val publicName: String = "",
    @ColumnInfo(name = "type")
    val type: Int = 0,
    @ColumnInfo(name = "username")
    val username: String = "",
)

fun UserEntity.asExternalModule() = User(
    id = id,
    coinCount = coinCount,
    email = email,
    icon = icon,
    nickname = nickname,
    username = username,
    publicName = publicName,
    type = type,
)
