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

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
