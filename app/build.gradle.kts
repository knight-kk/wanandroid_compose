plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = 33

    namespace = "com.wkk.wanandroid"

    defaultConfig {
        applicationId = "com.wkk.wanandroid"
        minSdk = 21
        targetSdk = 33
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    // Enable room auto-migrations

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":data:article"))
    implementation(project(":feature:article"))
    implementation(project(":feature:user"))
    implementation(project(":feature:course"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material.icon.extended)
    implementation(libs.compose.material3)

    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.compose.activity)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.paging.compose)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.flowlayout)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.test.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.junit.ktx)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.test)
}
