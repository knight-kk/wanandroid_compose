/*
 * Copyright 2023 knight-kk
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

import com.wkk.testing.MainDispatcherRule
import com.wkk.testing.runTestStateFlow
import com.wkk.user.screen.login.LoginUiState
import com.wkk.user.screen.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @Rule
    @JvmField
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(FakeUserRepository())
    }

    @Test
    fun `login Failed where login username is null`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect()
        }
        viewModel.login("", TestUser.password)
        assertTrue(viewModel.uiState.value is LoginUiState.Error)
        assertEquals("请输入用户名", (viewModel.uiState.value as LoginUiState.Error).message)
        collectJob.cancel()
    }

    @Test
    fun `login Failed where login password is null`() = runTestStateFlow(viewModel.uiState) {
        viewModel.login(TestUser.userName, "")
        assertTrue(viewModel.uiState.value is LoginUiState.Error)
        assertEquals("请输入密码", (viewModel.uiState.value as LoginUiState.Error).message)
    }

    @Test
    fun `login Failed where login userName and password error`() =
        runTestStateFlow(viewModel.uiState) {
            viewModel.login(TestUser.userName, "123")
            assertTrue(viewModel.uiState.value is LoginUiState.Error)
        }

    @Test
    fun `login Success where login userName or password error matching`() =
        runTestStateFlow(viewModel.uiState) {
            viewModel.login(TestUser.userName, TestUser.password)
            assertTrue(viewModel.uiState.value is LoginUiState.Success)
        }
}
