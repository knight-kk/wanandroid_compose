plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.wkk.article"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
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
}

dependencies {
    implementation(project(":data"))
    implementation(project(":data:model"))
    implementation(project(":data:network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)

    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material.icon.extended)
    implementation(libs.compose.material3)

    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.compose.activity)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.navigation.compose)
    implementation(libs.viewmodel.compose)
    implementation(libs.paging.compose)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    implementation(libs.accompanist.webview)

    testImplementation(libs.test.junit)
    testImplementation(libs.androidx.test.junit.ktx)
    androidTestImplementation(composeBom)
}
