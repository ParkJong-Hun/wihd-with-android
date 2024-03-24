// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidGradlePlugin) apply false
    alias(libs.plugins.androidGradleLibraryPlugin) apply false
    alias(libs.plugins.kotlinPlugin) apply false
    alias(libs.plugins.kspPlugin) apply false
    alias(libs.plugins.dependencyGuardPlugin) apply false
    alias(libs.plugins.roborazziPlugin) apply false
    alias(libs.plugins.crashlyticsPlugin) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

buildscript {
    configurations.all {
        resolutionStrategy.eachDependency {
            when (requested.name) {
                "javapoet" -> useVersion("1.13.0")
            }
        }
    }
}
