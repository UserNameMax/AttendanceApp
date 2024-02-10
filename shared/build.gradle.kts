repositories {
    mavenCentral()
}

plugins {
    kotlin("multiplatform") version "1.9.22"
}

kotlin {
    jvm{}
    sourceSets {
        val commonMain by getting{
            dependencies {

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
