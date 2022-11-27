plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.devtools.ksp") version "1.7.20-1.0.7" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.diffplug.spotless") version "6.1.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint("0.43.2")
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
