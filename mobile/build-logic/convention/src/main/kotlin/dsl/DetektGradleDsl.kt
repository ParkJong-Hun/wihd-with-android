package dsl

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

fun Project.setupDetekt(extension: DetektExtension) {
    extension.apply {
        parallel = true
        config.setFrom(files("${project.rootDir}/.detekt/detekt.yml"))
        baseline = file("${project.rootDir}/.detekt/baseline.xml")
        buildUponDefaultConfig = true
        ignoreFailures = false
        autoCorrect = false
    }

    @Suppress("UNCHECKED_CAST")
    val reportMerge = if (!rootProject.tasks.names.contains("reportMerge")) {
        rootProject.tasks.register("reportMerge", ReportMergeTask::class) {
            output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml"))
        }
    } else {
        rootProject.tasks.named("reportMerge") as TaskProvider<ReportMergeTask>
    }

    plugins.withType<io.gitlab.arturbosch.detekt.DetektPlugin> {
        tasks.withType<io.gitlab.arturbosch.detekt.Detekt> detekt@{
            finalizedBy(reportMerge)

            source = project.files("./").asFileTree

            include("**/*.kt")
            include("**/*.kts")
            exclude("**/resources/**")
            exclude("**/build/**")

            reportMerge.configure {
                input.from(this@detekt.xmlReportFile) // or .sarifReportFile
            }
        }
    }
}
