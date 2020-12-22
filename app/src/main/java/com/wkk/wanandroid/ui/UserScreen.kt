package com.wkk.wanandroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wkk.wanandroid.R

/**
 * 我的
 */

@Composable
fun UserScreen() {
    Box(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color(15,157,88)),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(id = R.string.main_tab_user))
    }
}


@Preview
@Composable
fun UserPreView() {
    UserScreen()
}