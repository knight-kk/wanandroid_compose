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

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.wkk.wanandroid.ui.components.CommonTopBar
import com.wkk.wanandroid.utils.UserManager
import kotlinx.coroutines.launch

/**
 * 我的
 */
@Composable
fun UserScreen(toLogin: () -> Unit) {
    Scaffold(
        topBar = { CommonTopBar("我的") }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UserHeader(UserManager.isLogin, toLogin)
        }
    }
}

@Composable
fun UserHeader(isLogin: Boolean, toLogin: () -> Unit) {
    if (isLogin) {
        UserLoginHeader()
        return
    }
    UserNoLoginHeader(toLogin)
}

@Composable
private fun UserNoLoginHeader(toLogin: () -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .border(1.dp, Color(0xFFE7E7E7), CircleShape)
                .size(60.dp)
                .clip(CircleShape),
            imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
            contentDescription = "user icon"
        )
        Button(onClick = toLogin) {
            Text(text = "登录")
        }
    }
}

@Composable
fun UserLoginHeader() {
    val (user, setUser) = remember { mutableStateOf<User?>(User(-1)) }
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .border(1.dp, Color(0xFFE7E7E7), CircleShape)
                    .size(60.dp)
                    .clip(CircleShape),
                imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                contentDescription = "user icon"
            )
            LaunchedEffect(user) {
                try {
                    setUser(UserManager.getLoginUser())
                } catch (e: Exception) {
                    Log.i("TAG", "UserLoginHeader: " + e.message)
                }
                Log.i("TAG", "LaunchedEffect UserLoginHeader: " + user)
            }
            if (user == null) return
            Text(text = user.nickname)
        }
        Button(onClick = {
            coroutineScope.launch {
                UserManager.logout()
            }
        }) {
            Text("退出登录")
        }
    }
}

@Preview
@Composable
fun UserPreView() {
    UserScreen {}
}
