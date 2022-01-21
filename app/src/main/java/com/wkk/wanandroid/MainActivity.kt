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
package com.wkk.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.wkk.wanandroid.constants.DataStoreConstants
import com.wkk.wanandroid.ui.LaunchScreen
import com.wkk.wanandroid.ui.MainScreen
import com.wkk.wanandroid.ui.login.LoginScreen
import com.wkk.wanandroid.utils.ext.dataStore
import com.wkk.wanandroid.utils.ext.getValue
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidApp()
        }
    }
}

@Composable
fun WanAndroidApp() {
    var loginStatus by remember { mutableStateOf(LoginStatus.LOADING) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.dataStore.getValue(booleanPreferencesKey(DataStoreConstants.IS_LOGIN_KEY))
            .catch { emit(false) }
            .collect { isLogin ->
                loginStatus = if (isLogin == true) LoginStatus.MAIN else LoginStatus.LOGIN
            }
    }
    when (loginStatus) {
        LoginStatus.LOADING -> LaunchScreen()
        LoginStatus.LOGIN -> LoginScreen()
        LoginStatus.MAIN -> MainScreen()
    }
}

private enum class LoginStatus {
    LOADING,
    LOGIN,
    MAIN
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidApp()
}
