plugins {
    alias(libs.plugins.wihd.library)
    alias(libs.plugins.wihd.kotlin)
    alias(libs.plugins.wihd.test)
    alias(libs.plugins.wihd.detekt)
    alias(libs.plugins.wihd.serialization)
}

android.namespace = "co.kr.parkjonghun.whatishedoingwithandroid.data.interior"

dependencies {
    implementation(project(":core:domain:service"))
    implementation(project(":core:data:dao"))
    implementation(libs.supabase.db)
}
