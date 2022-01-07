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
package com.wkk.wanandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.wkk.wanandroid.ui.blue200
import com.wkk.wanandroid.ui.blue500
import com.wkk.wanandroid.ui.blue700
import com.wkk.wanandroid.ui.orange200
import com.wkk.wanandroid.ui.shapes

//  [Compose 中的主题](https://developer.android.google.cn/jetpack/compose/themes)
//  [ Jetpack Compose 主题 Codelab](https://developer.android.google.cn/codelabs/jetpack-compose-theming#0)

private val DarkColorPalette = darkColors(
    primary = blue200,
    primaryVariant = blue700,
    secondary = orange200
)

private val LightColorPalette = lightColors(
    primary = blue500,
    primaryVariant = blue700,
    secondary = orange200,
)

@Composable
fun WanandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
