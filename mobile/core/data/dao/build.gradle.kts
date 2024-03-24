plugins {
    alias(libs.plugins.wihd.library)
    alias(libs.plugins.wihd.test)
    alias(libs.plugins.wihd.detekt)
    alias(libs.plugins.wihd.serialization)
    alias(libs.plugins.wihd.firebase)
}

android.namespace = "co.kr.parkjonghun.whatishedoingwithandroid.data.dao"

dependencies {
    implementation(libs.jetpack.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.jetpack.datastore.preferences)
    implementation(libs.ktor)
    implementation(libs.timber)
    implementation(libs.supabase.db)
}
