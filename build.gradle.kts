plugins {
    id("com.android.application") version "7.4.0-alpha05" apply false
    id("com.android.library") version "7.4.0-alpha05" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("com.google.devtools.ksp") version "1.6.10-1.0.2" apply false
    id("com.diffplug.spotless") version "6.1.0"
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
