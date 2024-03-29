plugins {
    alias(libs.plugins.wihd.library)
    alias(libs.plugins.wihd.compose)
    alias(libs.plugins.wihd.detekt)
    alias(libs.plugins.wihd.test)
}

android.namespace = "co.kr.parkjonghun.whatishedoingwithandroid.ui.system"

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.supabase.auth)
    implementation(libs.supabase.auth.ui)
}
