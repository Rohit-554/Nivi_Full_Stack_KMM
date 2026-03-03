plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

val isAndroidProd = gradle.startParameter.taskRequests.toString().contains("Prod", ignoreCase = true)
project.ext.set("APP_ENV", if (isAndroidProd) "prod" else "qa")

android {
    namespace = "io.jadu.nivi"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    flavorDimensions += "environment"

    productFlavors {
        create("qa") {
            dimension = "environment"
            applicationIdSuffix = ".qa"
        }
        create("prod") {
            dimension = "environment"
        }
    }

    defaultConfig {
        applicationId = "io.jadu.nivi"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(projects.composeApp)
    implementation(libs.androidx.material3)
    implementation(libs.compose.uiToolingPreview)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.startup:startup-runtime:1.2.0")
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation("io.insert-koin:koin-android:4.1.1")

}