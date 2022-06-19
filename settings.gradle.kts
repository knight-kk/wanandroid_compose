// https://stackoverflow.com/questions/63282922/buildfeatures-is-unstable-because-its-signature-references-unstable-marked-w
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        maven("https://maven.aliyun.com/repository/google")
//        maven("https://maven.aliyun.com/repository/public")
        google()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "wanAndroid"
include(":app")
