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
package com.wkk.wanandroid.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wkk.wanandroid.R
import com.wkk.wanandroid.model.User
import com.wkk.wanandroid.utils.UserManager
import kotlinx.coroutines.launch

@Composable
fun UserInfoCard(user: User) {
    Card(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            DefaultUserIcon()
            UserInfo(user)
        }
    }
}

@Composable
fun UserInfo(user: User?) {
    if (user == null) return
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = user.nickname,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = user.email,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun UserNoLoginHeader(toLogin: () -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        DefaultUserIcon()
        Button(onClick = toLogin) {
            Text(text = "登录")
        }
    }
}

@Composable
fun UserHeader() {
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxWidth()) {
        Button(onClick = {
            coroutineScope.launch {
                UserManager.logout()
            }
        }) {
            Text("退出登录")
        }
    }
}

@Composable
private fun DefaultUserIcon() {
    Image(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .border(1.dp, Color(0xFFE7E7E7), CircleShape)
            .size(60.dp)
            .clip(CircleShape),
        imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
        contentDescription = "user icon"
    )
}

@Preview
@Composable
fun UserInfoCardPreview() {
    UserInfoCard(User(id = 1, nickname = "knight", email = "wkk321@outlook.com"))
}
