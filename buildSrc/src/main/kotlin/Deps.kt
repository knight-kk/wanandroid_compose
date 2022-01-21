object Deps {
    /**
     * AndroidX
     */
    object Androidx {
        const val NAV_COMPOSE = "androidx.navigation:navigation-compose:2.4.0-rc01"

        object Common {
            const val CORE_KTX = "androidx.core:core-ktx:1.7.0"
            const val APPCOMPAT = "androidx.appcompat:appcompat:1.3.0"
            const val MATERIAL = "com.google.android.material:material:1.3.0"
        }

        object Lifecycle {
            private const val VERSION = "2.4.0"
            const val RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
            const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:1.4.0"
        }

        object Compose {
            const val VERSION = "1.0.5"
            const val UI = "androidx.compose.ui:ui:$VERSION"
            const val MATERIAL = "androidx.compose.material:material:$VERSION"
            const val UI_TOOLING = "androidx.compose.ui:ui-tooling:$VERSION"
            const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$VERSION"
            const val UI_TEST_JUNIT = "androidx.compose.ui:ui-test-junit4:$VERSION"
        }

        object DataStore {
            const val VERSION = "1.0.0"
            const val DATASTORE = "androidx.datastore:datastore:$VERSION"
            const val DATASTORE_PREFERENCES = "androidx.datastore:datastore-preferences:$VERSION"
        }

        object Test {
            private const val JUNIT_VERSION = "1.1.3"
            private const val ESPRESSO_VERSION = "3.4.0"
            const val EXT_JUNIT = "androidx.test.ext:junit-ktx:$JUNIT_VERSION"
            const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
        }
    }

    object Google {
        /**
         * Compose 扩展库
         */
        object Accompanist {
            private const val VERSION = "0.22.0-rc"
            const val PAGER = "com.google.accompanist:accompanist-pager:$VERSION"
            const val NAV_ANIMATION = "com.google.accompanist:accompanist-navigation-animation:$VERSION"
        }
    }

    object Squareup {
        private const val OKHTTP_VERSION = "4.9.1"
        private const val RETROFIT_VERSION = "2.9.0"
        private const val MOSHI_VERSION = "1.12.0"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
        const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
        const val RETROFIT_CONVERTER_MOSHI =
            "com.squareup.retrofit2:converter-moshi:$RETROFIT_VERSION"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:$MOSHI_VERSION"
        const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:$MOSHI_VERSION"
    }

    /**
     * 测试
     */
    object Test {
        private const val JUNIT_VERSION = "4.13.1"
        const val JUNIT = "junit:junit:$JUNIT_VERSION"
    }
}
