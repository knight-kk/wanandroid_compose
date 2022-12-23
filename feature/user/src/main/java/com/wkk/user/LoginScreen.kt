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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.PersonPinCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wkk.model.DataResult
import com.wkk.user.components.PasswordTextField
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), onClosePage: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClosePage) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "close"
                        )
                    }
                },
                title = { Text(text = "登录") }
            )
        }
    ) { paddingValues ->
        val coroutineScope = rememberCoroutineScope()
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.32f),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "logo"
            )
            var userName by remember { mutableStateOf("") }
            UserTextField(
                value = userName,
                onValueChange = { value ->
                    userName = value
                },
                onClear = { userName = "" }
            )

            var password by remember { mutableStateOf("") }
            PasswordTextField(
                value = password,
                onValueChange = { value ->
                    password = value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Row(Modifier.fillMaxWidth()) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "忘记密码?")
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    coroutineScope.launch {
                        val result = viewModel.login(userName, password)
                        if (result is DataResult.Error) {
                            snackbarHostState.showSnackbar(result.message)
                            return@launch
                        }
                        onClosePage()
                    }
                }
            ) {
                Text(text = "登录")
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "注册")
            }
        }
    }
}

@Composable
private fun UserTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "用户名") },
        leadingIcon = {
            Icon(Icons.Rounded.PersonPinCircle, contentDescription = "", tint = Color.Gray)
        },
        trailingIcon = {
            IconButton(onClick = onClear) {
                Icon(Icons.Rounded.Clear, contentDescription = "")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreView() {
    LoginScreen {}
}
