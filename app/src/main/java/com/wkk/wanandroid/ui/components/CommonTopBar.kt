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
package com.wkk.wanandroid.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CommonTopBar(title: String?, onBack: (() -> Unit)? = null) {
    CenterAlignedTopAppBar(
        title = { if (!title.isNullOrEmpty()) Text(title) },
        navigationIcon = {
            if (onBack == null) return@CenterAlignedTopAppBar
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "icon")
            }
        }
    )
}
