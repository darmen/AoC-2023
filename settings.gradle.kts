plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "AoC-2023"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
