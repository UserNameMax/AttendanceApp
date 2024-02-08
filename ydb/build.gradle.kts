repositories {
    mavenCentral()
}

plugins {
    kotlin("multiplatform") version "1.9.0"
}

kotlin {
    jvm{}
    sourceSets {
        val commonMain by getting{
            dependencies {
                implementation(project(":shared"))
                api("tech.ydb:ydb-sdk-bom:2.1.11")
                api("tech.ydb:ydb-sdk-table:2.1.11")
                api("tech.ydb:ydb-sdk-topic:2.1.11")
                api("tech.ydb:ydb-sdk-scheme:2.1.11")
                api("tech.ydb:ydb-sdk-coordination:2.1.11")
                api("tech.ydb.auth:yc-auth-provider:2.1.1")
                api("io.perfmark:perfmark-api:0.27.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
