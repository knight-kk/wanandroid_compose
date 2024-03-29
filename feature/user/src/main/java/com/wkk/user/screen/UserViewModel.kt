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
package com.wkk.user.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wkk.model.User
import com.wkk.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val userUiState: StateFlow<UserUiState> = userUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UserUiState.Loading,
        )

    private fun userUiState(): Flow<UserUiState> {
        return userRepository.getUserInfo()
            .map<User, UserUiState> {
                UserUiState.Success(it)
            }
            .onStart {
                userRepository.fetchUserInfo()
                emit(UserUiState.Loading)
            }
            .catch { emit(UserUiState.Error(it.message ?: "")) }
    }

    suspend fun logout() {
        userRepository.logout()
    }

    suspend fun fetchUserInfo() = userRepository.fetchUserInfo()
}

sealed interface UserUiState {
    data class Success(val user: User) : UserUiState
    data class Error(val message: String) : UserUiState
    object Loading : UserUiState
}
