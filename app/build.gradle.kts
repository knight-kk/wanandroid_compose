plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.wkk.wanandroid"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Androidx.Compose.VERSION
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Deps.Androidx.Common.CORE_KTX)
    implementation(Deps.Androidx.Common.APPCOMPAT)
    implementation(Deps.Androidx.Compose.UI)
    implementation(Deps.Androidx.Compose.MATERIAL)
    implementation(Deps.Androidx.Compose.MATERIAL3)
    implementation(Deps.Androidx.Compose.UI_TOOLING)
    implementation(Deps.Androidx.Compose.UI_TOOLING_PREVIEW)

    implementation(Deps.Androidx.Lifecycle.RUNTIME_KTX)
    implementation(Deps.Androidx.Lifecycle.ACTIVITY_COMPOSE)
    implementation(Deps.Androidx.DataStore.DATASTORE_PREFERENCES)
    implementation(Deps.Androidx.NAV_COMPOSE)

    implementation(Deps.Google.Accompanist.PAGER)
    implementation(Deps.Google.Accompanist.NAV_ANIMATION)

    implementation(Deps.Squareup.OKHTTP)
    implementation(Deps.Squareup.OKHTTP_LOGGING)
    implementation(Deps.Squareup.RETROFIT)
    implementation(Deps.Squareup.RETROFIT_CONVERTER_MOSHI)
    implementation(Deps.Squareup.MOSHI)
    ksp(Deps.Squareup.MOSHI_CODEGEN)

    testImplementation(Deps.Test.JUNIT)
    androidTestImplementation(Deps.Androidx.Test.EXT_JUNIT)
    androidTestImplementation(Deps.Androidx.Test.ESPRESSO_CORE)
    androidTestImplementation(Deps.Androidx.Compose.UI_TEST_JUNIT)
}
