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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wkk.model.CoinRecord
import com.wkk.ui.components.appTopBar

@Composable
fun UserCoinScreen(navigateUp: () -> Unit, viewModel: UserCoinViewModel = hiltViewModel()) {
    Scaffold(
        topBar = appTopBar("积分明细", navigateUp),
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            val items = viewModel.fetchCoinRecord.collectAsLazyPagingItems()
            UserCoinRecordList(items)
        }
    }
}

@Composable
private fun UserCoinRecordList(items: LazyPagingItems<CoinRecord>) {
    LazyColumn {
        items(items, key = { it.id }) { coinRecord: CoinRecord? ->
            if (coinRecord == null) return@items
            CoinRecordItem(coinRecord)
            Divider()
        }
    }
}

@Composable
fun CoinRecordItem(coinRecord: CoinRecord) {
    Row(Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            Text(coinRecord.desc)
            Text(coinRecord.reason)
        }
        Text(text = "${if (coinRecord.type == 1) "+" else "-"}${coinRecord.coinCount}")
    }
}
