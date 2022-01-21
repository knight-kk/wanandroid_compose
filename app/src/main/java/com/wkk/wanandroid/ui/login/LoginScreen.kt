/*
 * Copyright 2021 knight-kk
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
package com.wkk.wanandroid.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wkk.wanandroid.net.LoginManager
import com.wkk.wanandroid.net.NetManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize()) {
        Button(onClick = {
            coroutineScope.launch {
                val result = NetManager.apiService.login("*****", "*****")
                if (result.isSuccess()) {
                    LoginManager.login()
                }
            }
        }) {
            Text(text = "登录")
        }
        Button(onClick = {
            coroutineScope.launch {
                NetManager.apiService.unReadMessageCount()
            }
        }) {
            Text(text = "未读消息")
        }

        Button(onClick = {
            coroutineScope.launch {
                val result = NetManager.apiService.logout()
                if (result.isSuccess()) {
                    LoginManager.logout()
                }
            }
        }) {
            Text(text = "退出登录")
        }
    }
}

@Preview(showBackground = false)
@Composable
fun LoginPreView() {
    LoginScreen()
}
