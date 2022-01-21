// https://stackoverflow.com/questions/63282922/buildfeatures-is-unstable-because-its-signature-references-unstable-marked-w
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "wanAndroid"
include(":app")
