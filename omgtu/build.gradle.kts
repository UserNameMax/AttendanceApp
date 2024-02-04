repositories {
    mavenCentral()
}

plugins {
    kotlin("multiplatform") version "1.9.0"

    id("kotlinx-serialization")
}

val selenium_version = "4.17.0"

kotlin {
    jvm{}
    sourceSets {
        val commonMain by getting{
            dependencies {
                implementation(project(":shared"))
                // ktor
                api("io.ktor:ktor-client-core:2.3.4")
                api("io.ktor:ktor-client-cio:2.3.4")
                api("io.ktor:ktor-client-content-negotiation:2.3.4") //serialization
                api("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
                api("io.ktor:ktor-client-logging:2.3.4") //logger

                // selenium
                api("org.seleniumhq.selenium:selenium-java:${selenium_version}")
                api("org.seleniumhq.selenium:selenium-support:${selenium_version}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
