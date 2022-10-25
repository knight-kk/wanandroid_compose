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
package com.wkk.wanandroid.ui.tree

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wkk.wanandroid.model.KnowledgeTree
import com.wkk.wanandroid.ui.tree.components.KnowledgeTreeItem
import com.wkk.wanandroid.ui.tree.vm.KnowledgeTreeViewModel

@Composable
fun KnowledgeTreeScreen(viewModel: KnowledgeTreeViewModel = hiltViewModel()) {
    val knowledgeTrees = viewModel.knowledgeTrees.collectAsState(emptyList<KnowledgeTree>()).value
    Scaffold(topBar = { SmallTopAppBar(title = { Text(text = "体系") }) }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            LazyColumn {
                items(knowledgeTrees, key = { it.id }) { knowledgeTree ->
                    KnowledgeTreeItem(knowledgeTree)
                }
            }
        }
    }
}
