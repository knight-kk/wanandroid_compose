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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wkk.wanandroid.R

@Composable
fun LoginScreen() {
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "logo"
        )
        var userName by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            value = userName,
            onValueChange = { value ->
                userName = value
            },
            label = { Text(text = "用户名") }
        )

        var password by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            value = password,
            onValueChange = { value ->
                password = value
            }, label = { Text(text = "密码") }
        )

        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = { }
        ) {
            Text(text = "登录")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreView() {
    LoginScreen()
}
