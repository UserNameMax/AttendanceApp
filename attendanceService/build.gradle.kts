repositories {
    mavenCentral()
}

plugins {
    kotlin("multiplatform") version "1.9.0"
    id("io.ktor.plugin") version "2.3.8"
}

val ktorVersion = "2.3.8"
val koin_version = "3.5.3"

kotlin {
    jvm{}
    sourceSets {
        val jvmMain by getting{
            dependencies {
                implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

                implementation("io.insert-koin:koin-core:$koin_version")
                implementation("io.insert-koin:koin-ktor:$koin_version")

                implementation(project(":shared"))
                implementation(project(":ydb"))
                implementation(project(":omgtu"))
                implementation(project(":attendanceApi"))
            }
        }
    }
}
