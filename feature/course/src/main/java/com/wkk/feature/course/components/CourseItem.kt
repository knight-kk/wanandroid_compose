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
package com.wkk.feature.course.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wkk.model.Course
import com.wkk.ui.theme.AppTheme

@Composable
fun CourseItem(course: Course, modifier: Modifier = Modifier, onItemClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .then(modifier),
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color.LightGray)
                .size(95.dp, 133.dp),
            model = course.cover,
            contentDescription = "cover"
        )
        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {
            Text(text = course.name, style = MaterialTheme.typography.titleMedium)
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "作者:${course.author}",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = course.desc.trim(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4
            )
        }
    }
}

@Preview()
@Composable
fun CourseItemPreView() {
    AppTheme(darkTheme = true) {
        CourseItem(
            course = Course(
                name = "阮一峰",
                author = "阮一峰",
                desc = "C 语言入门教程。",
            ),
        ) {
        }
    }
}
