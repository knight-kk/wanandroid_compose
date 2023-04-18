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
package com.wkk.user

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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

/**
 * 我的
 */
@Composable
fun UserScreen(userViewModel: UserViewModel = hiltViewModel(), navigateToLogin: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val userUiState by userViewModel.userUiState.collectAsStateWithLifecycle()
    Scaffold(topBar = { TopAppBar(title = { Text(text = "我的") }) }) { paddingValues ->
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            UserInfoHeader(userUiState, navigateToLogin)

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

            UserLogoutButton {
                coroutineScope.launch {
                    userViewModel.logout()
                    navigateToLogin()
                }
            }
        }
    }
}

@Composable
fun UserMenuItem(
    icon: ImageVector,
    itemName: String,
    hideDividerLine: Boolean = false,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = icon, contentDescription = "icon", Modifier.padding(horizontal = 16.dp))
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            Row(Modifier.align(Alignment.Center)) {
                Text(
                    text = itemName,
                    Modifier.weight(1f),
                    style = MaterialTheme.typography.labelLarge,
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "arrow",
                    Modifier.padding(end = 16.dp),
                )
            }
            if (hideDividerLine.not()) {
                Divider(Modifier.align(Alignment.BottomEnd))
            }
        }
    }
}

@Composable
fun UserLogoutButton(logout: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth(),
        onClick = logout,
    ) {
        Text("退出登录")
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
