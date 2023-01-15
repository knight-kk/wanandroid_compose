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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkk.model.DataResult
import com.wkk.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.None)

    val uiState: StateFlow<LoginUiState> =
        _uiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LoginUiState.None
        )

    suspend fun login(userName: String, password: String) {
        _uiState.value = LoginUiState.Loading
        if (userName.isEmpty()) {
            _uiState.value = LoginUiState.Error("请输入用户名")
            return
        }
        if (password.isEmpty()) {
            _uiState.value = LoginUiState.Error("请输入密码")
            return
        }
        val result = userRepository.login(userName, password)
        _uiState.value = when (result) {
            is DataResult.Error -> LoginUiState.Error(result.message)
            is DataResult.Success -> LoginUiState.Success
        }
    }
}

sealed interface LoginUiState {
    object None : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}
