plugins {
    alias(libs.plugins.wihd.library)
    alias(libs.plugins.wihd.kotlin)
    alias(libs.plugins.wihd.test)
    alias(libs.plugins.wihd.detekt)
    alias(libs.plugins.wihd.parcelize)
}

android.namespace = "co.kr.parkjonghun.whatishedoingwithandroid.domain.service"

dependencies {
    api(project(":core:domain:base"))
    implementation(libs.jetpack.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
}
