import dsl.android
import dsl.implementation
import dsl.implementationPlatform
import dsl.library
import dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class FirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }

            android {
                packagingOptions {
                    resources {
                        excludes += "META-INF/gradle/incremental.annotation.processors"
                    }
                }
            }
            dependencies {
                implementationPlatform(libs.library("firebase-bom"))
                implementation(libs.library("firebase-common"))
                implementation(libs.library("firebase-analytics"))
                implementation(libs.library("firebase-crashlytics"))
            }
        }
    }
}
