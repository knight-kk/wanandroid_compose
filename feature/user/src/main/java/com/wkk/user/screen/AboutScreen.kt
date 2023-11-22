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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wkk.model.AppInfo
import com.wkk.ui.components.appTopBar

@Composable
fun AboutScreen(
    appInfo: AppInfo,
    navigateUp: () -> Unit,
    navigate: (route: String) -> Unit,
) {
    Scaffold(
        topBar = appTopBar("关于", navigateUp),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.24f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.wrapContentSize(),
                    imageVector = ImageVector.vectorResource(id = com.wkk.ui.R.drawable.ic_logo),
                    contentDescription = "logo",
                )

                Text(text = "版本号：" + appInfo.versionName)
            }

            OutlinedCard(Modifier.padding(10.dp)) {
                ListItem("官网地址") {
                    // TODO wkk 跳转
                }

                Divider()

                ListItem("项目地址") {
                    // TODO wkk 跳转
                }
            }
        }
    }
}

@Composable
fun ListItem(
    itemName: String,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = itemName,
            Modifier.weight(1f),
            style = MaterialTheme.typography.labelLarge,
        )
        Icon(
            imageVector = Icons.Rounded.ArrowForwardIos,
            contentDescription = "arrow",
            Modifier.padding(end = 16.dp),
        )
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        AppInfo("玩Android", 1, "1.0.0"),
        {},
        {},
    )
}
