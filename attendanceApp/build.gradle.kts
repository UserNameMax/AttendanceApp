repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

plugins {
    kotlin("multiplatform") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.12"
    id("kotlinx-serialization")
    kotlin("android") version "1.9.22" apply false
    id("com.android.application") version "8.1.0"
}

val ktorVersion = "2.3.8"
val koinVersion = "3.5.3"
val decomposeVersion = "2.2.2"

kotlin {
    jvm {}
    androidTarget(){
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(project(":attendanceApi"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")

                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("io.insert-koin:koin-compose:3.6.0-alpha1")
            }
        }
        val jvmMain by getting{
            dependencies{
                implementation(compose.desktop.currentOs)
            }
        }
        val androidMain by getting{
            dependencies{
                implementation("androidx.activity:activity-compose:1.8.2")
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation(compose.uiTooling)

                implementation("com.arkivanov.decompose:extensions-android:$decomposeVersion")
            }
        }
    }
}
android {
    namespace = "ru.omgtu.ivt213.mishenko.maksim.attendance"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.omgtu.ivt213.mishenko.maksim.attendance"
        versionCode = 1
        versionName = "0.0.1"

        minSdk = 26
        targetSdk = 34
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}