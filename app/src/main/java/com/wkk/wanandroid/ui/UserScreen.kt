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
package com.wkk.wanandroid.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wkk.wanandroid.R
import com.wkk.wanandroid.net.LoginManager
import com.wkk.wanandroid.ui.components.CommonTopBar

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
            UserHeader(toLogin)
        }
    }
}

@Composable
fun UserHeader(toLogin: () -> Unit) {
    if (LoginManager.isLogin.not()) {
        UserNoLoginHeader(toLogin)
        return
    }
    UserLoginHeader()
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
        Text(text = "已登录")
    }
}


@Preview
@Composable
fun UserPreView() {
    UserScreen {}
}
