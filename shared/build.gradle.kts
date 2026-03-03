import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.gmazzo.buildconfig)
}

kotlin {

    val taskReq = gradle.startParameter.taskRequests.toString()
    val isAndroidProd = taskReq.contains("Prod", ignoreCase = true)
    val isAndroidRelease = taskReq.contains("Release", ignoreCase = true)

    val appEnv = System.getenv("APP_ENV") ?: if (isAndroidProd) "prod" else "qa"

    val isAppRelease = System.getenv("APP_RELEASE")?.toBoolean() ?: isAndroidRelease

    val isProd = appEnv == "prod"
    val androidBaseUrl = if (isProd) "https://android.api.example.com" else "http://10.0.2.2:8080"
    val iosBaseUrl     = if (isProd) "https://ios.api.example.com"     else "http://localhost:8080"
    val jvmBaseUrl     = "http://localhost:8080"

    buildConfig {
        packageName("io.jadu.nivi.shared")
        useKotlinOutput { internalVisibility = false }

        // Common fields
        buildConfigField("BASE_URL", expect<String>())
        buildConfigField("IS_ANDROID", expect(false))
        buildConfigField("IS_DEBUG", !isAppRelease)
        buildConfigField("ENV_NAME", if (isProd) "Production" else "QA")

        // Per-platform overrides
        sourceSets.named("androidMain") {
            buildConfigField("IS_ANDROID", true)
            buildConfigField("BASE_URL", androidBaseUrl)
        }

        listOf("iosMain", "iosArm64Main", "iosSimulatorArm64Main").forEach { name ->
            sourceSets.maybeCreate(name).apply {
                buildConfigField("BASE_URL", iosBaseUrl)
            }
        }

        sourceSets.maybeCreate("jvmMain").apply {
            buildConfigField("BASE_URL", jvmBaseUrl)
        }

    }


    androidLibrary {
        namespace = "io.jadu.nivi.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }


    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidUnitTest.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosTest.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        jvmTest.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }
}


