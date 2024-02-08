pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "attendance"
include("shared")
include("ydb")
include("attendanceService")
include("syncService")
include("omgtu")
include("attendanceApi")
include("attendanceApp")