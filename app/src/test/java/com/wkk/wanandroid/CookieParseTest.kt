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
package com.wkk.wanandroid

import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Assert
import org.junit.Test

class CookieParseTest {
    @Test
    fun test() {
//        val cookieStr =
//            "loginUserName_wanandroid_com=knight; expires=Mon, 07 Feb 2022 14:28:41 GMT; domain=wanandroid.com; path=/"
        val cookieStr = """loginUserPassword_wanandroid_com=""; Domain=wanandroid.com; Expires=Thu, 01-Jan-1970 00:00:10 GMT; Path=/"""
        val url = "https://www.wanandroid.com".toHttpUrl()
        val cookie = Cookie.parse(url, cookieStr)
        Assert.assertEquals(cookie.toString(), cookieStr)
    }
}
