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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.ListAlt
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wkk.wanandroid.model.User
import com.wkk.wanandroid.ui.components.CommonTopBar
import com.wkk.wanandroid.utils.UserManager
import kotlinx.coroutines.launch

/**
 * 我的
 */
@Composable
fun UserScreen(toLogin: () -> Unit) {
    val (user, setUser) = remember { mutableStateOf(User(-1)) }
    LaunchedEffect(user) {
        UserManager.getLoginUser()?.let { setUser(it) }
    }
    Scaffold(topBar = { CommonTopBar("我的") }) { paddingValues ->
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UserHeader(UserManager.isLogin, user, toLogin)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(Modifier.padding(vertical = 10.dp)) {
                UserMenuItem(Icons.Rounded.Notifications, "消息") {

                }
                UserMenuItem(Icons.Rounded.ListAlt, "待办清单") {

                }
                UserMenuItem(Icons.Rounded.History, "阅读记录", true) {

                }
            }

            OutlinedCard(Modifier.padding(vertical = 10.dp)) {
                UserMenuItem(Icons.Rounded.Info, "关于") {

                }
                UserMenuItem(Icons.Rounded.Settings, "设置", true) {

                }
            }


            if (UserManager.isLogin) {
                UserSetting()
            }
        }
    }
}

@Composable
fun UserMenuItem(
    icon: ImageVector, itemName: String, hideDividerLine: Boolean = false, onClick: () -> Unit
) {
    Row(
        Modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = "icon", Modifier.padding(horizontal = 16.dp))
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Row(Modifier.align(Alignment.Center)) {
                Text(
                    text = itemName,
                    Modifier.weight(1f),
                    style = MaterialTheme.typography.labelLarge
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "arrow",
                    Modifier.padding(end = 16.dp)
                )
            }
            if (hideDividerLine.not()) {
                Divider(Modifier.align(Alignment.BottomEnd))
            }

        }
    }
}

@Composable
fun UserSetting(modifier: Modifier = Modifier) {


    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Button(modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth(), onClick = {
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
fun UserScreenPreView() {
    UserScreen {}
}


@Preview
@Composable
fun MenuItemPreView() {
    UserMenuItem(Icons.Rounded.Settings, "设置") {

    }
}
