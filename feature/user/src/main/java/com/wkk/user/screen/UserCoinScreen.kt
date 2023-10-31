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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.wkk.model.CoinRecord
import com.wkk.ui.components.appTopBar
import com.wkk.ui.components.paging.pagingFooter

@Composable
fun UserCoinScreen(navigateUp: () -> Unit, viewModel: UserCoinViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = appTopBar("积分明细", navigateUp, scrollBehavior),
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            val items = viewModel.fetchCoinRecord.collectAsLazyPagingItems()
            UserCoinRecordList(items)
        }
    }
}

@Composable
private fun UserCoinRecordList(
    items: LazyPagingItems<CoinRecord>,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    if (items.itemCount == 0) {
        CircularProgressIndicator()
        return
    }
    LazyColumn(state = lazyListState) {
        items(items, key = { it.id }) { coinRecord: CoinRecord? ->
            if (coinRecord == null) return@items
            CoinRecordItem(coinRecord)
            Divider()
        }
        pagingFooter(items.loadState.append)
    }
}

@Composable
fun CoinRecordItem(coinRecord: CoinRecord) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                coinRecord.reason,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                "${coinRecord.date},${coinRecord.desc}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            text = "${if (coinRecord.type == 1) "+" else "-"}${coinRecord.coinCount}",
            fontWeight = FontWeight.SemiBold,
            color = if (coinRecord.type == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
