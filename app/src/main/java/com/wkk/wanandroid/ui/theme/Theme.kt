package com.wkk.wanandroid.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

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

