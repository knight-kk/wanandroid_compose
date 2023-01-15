// https://stackoverflow.com/questions/63282922/buildfeatures-is-unstable-because-its-signature-references-unstable-marked-w
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
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

include(":core:model")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:ui")

include(":data:article")
include(":data:user")

include(":feature:article")
include(":feature:user")
include(":core:testing")
