repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm")
    id("io.ktor.plugin") version "2.3.8"
    application
}

val ktorVersion = "2.3.8"
val koin_version = "3.5.3"

application {
    mainClass.set("ru.omgtu.ivt213.mishenko.maksim.attendance.MainKt")
}

dependencies{
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")

    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")

    implementation(project(":shared"))
    implementation(project(":ydb"))
    implementation(project(":omgtu"))
    implementation(project(":attendanceApi"))
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_20)
        localImageName.set("attendance-service")
    }
    jib {
        container.mainClass = "ru.omgtu.ivt213.mishenko.maksim.attendance.MainKt"
    }
}
