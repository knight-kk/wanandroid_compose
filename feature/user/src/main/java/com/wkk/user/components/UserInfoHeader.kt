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
package com.wkk.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wkk.model.User
import com.wkk.user.screen.UserUiState

@Composable
fun UserInfoHeader(userUiState: UserUiState, navigateToLogin: () -> Unit) {
    when (userUiState) {
        is UserUiState.Error,
        UserUiState.Loading,
        -> UserNoLoginHeader(navigateToLogin)
        is UserUiState.Success -> UserInfoCard(user = userUiState.user)
    }
}

@Composable
private fun UserInfoCard(user: User) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DefaultUserIcon(user.nickname.trim())
            UserInfo(user, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = "arrow")
        }

        Row(
            Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { }
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${user.coinCount}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(text = "积分", style = MaterialTheme.typography.labelSmall)
            }
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxHeight()
                    .width(DividerDefaults.Thickness),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { }
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "--",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(text = "收藏", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun UserInfo(user: User?, modifier: Modifier = Modifier) {
    if (user == null) return
    Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = user.nickname,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                text = "LV100",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }

        Text(
            text = user.email,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun UserNoLoginHeader(navigateToLogin: () -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        DefaultUserIcon()
        Button(onClick = navigateToLogin) {
            Text(text = "登录")
        }
    }
}

@Composable
private fun DefaultUserIcon(userName: String = "--") {
    val content = if (userName.isNotEmpty()) userName.substring(0 until 1) else ""
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .size(60.dp)
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = content,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun UserInfoCardPreview() {
    UserInfoCard(User(id = "1", nickname = "knight", email = "wkk321@outlook.com"))
}
