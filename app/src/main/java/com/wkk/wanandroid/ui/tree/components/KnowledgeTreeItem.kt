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
package com.wkk.wanandroid.ui.tree.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.wkk.wanandroid.model.KnowledgeTree

@Composable
fun KnowledgeTreeItem(knowledgeTree: KnowledgeTree) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = knowledgeTree.name)
        FlowRow(mainAxisSpacing = 6.dp) {
            knowledgeTree.tags.map { tag ->
                AssistChip(onClick = { /*TODO*/ }, label = { Text(text = tag.name) })
            }
        }
    }
}

@Preview
@Composable
fun KnowledgeTreeItemPreview() {

    val knowledgeTree = KnowledgeTree(
        name = "Android Studio相关",
        tags = List(10) { KnowledgeTree.Tag(it.toLong(), "item$it") }
    )
    KnowledgeTreeItem(knowledgeTree)
}
