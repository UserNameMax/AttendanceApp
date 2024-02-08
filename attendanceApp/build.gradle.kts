repositories {
    mavenCentral()
}

plugins {
    kotlin("multiplatform") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.12"
    id("kotlinx-serialization")
}

val ktorVersion = "2.3.8"
val koinVersion = "3.5.3"
val decomposeVersion = "2.2.2"

kotlin {
    jvm {}
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
    }
}
