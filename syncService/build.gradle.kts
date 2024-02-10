repositories {
    mavenCentral()
}

plugins {
    kotlin("multiplatform") version "1.9.22"
}

val koin_version = "3.5.3"

kotlin {
    jvm{}
    sourceSets {
        val jvmMain by getting{
            dependencies {
                implementation(project(":shared"))
                implementation(project(":ydb"))
                implementation(project(":omgtu"))
                implementation("io.insert-koin:koin-core:$koin_version")
            }
        }
    }
}
