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
package com.wkk.utils

import java.util.concurrent.TimeUnit

object DateTimeUtils {

    private val MINUTE = TimeUnit.MINUTES.toMillis(1)
    private val HOUR = TimeUnit.HOURS.toMillis(1)
    private val DAY = TimeUnit.DAYS.toMillis(1)
    private val WEEK = TimeUnit.DAYS.toMillis(1) * 7
    private val MONTH = TimeUnit.DAYS.toMillis(1) * 30
    private val YEAR = TimeUnit.DAYS.toMillis(1) * 365

    fun formatDate(timeMillis: Long): String {
        val time = System.currentTimeMillis() - timeMillis
        if (time < MINUTE) {
            return "刚刚"
        }
        if (time < HOUR) {
            return "${time / MINUTE}分钟前"
        }

        if (time < DAY) {
            return "${time / HOUR}小时前"
        }

        if (time < WEEK) {
            return "${time / DAY}天前"
        }

        if (time < MONTH) {
            return "${time / WEEK}周前"
        }

        if (time < YEAR) {
            return "${time / MONTH}月前"
        }

        return "${time / YEAR}年前"
    }
}
