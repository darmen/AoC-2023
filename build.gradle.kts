plugins {
    kotlin("jvm") version "1.9.20"
}

group = "com.heydarmen.adventofcode"
version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation("io.github.p-org.solvers:z3:4.8.14-v5")
    implementation("net.sf.jung:jung-algorithms:2.1.1")
    implementation("net.sf.jung:jung-graph-impl:2.1.1")
    testImplementation(kotlin("test"))
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

tasks.test {
    useJUnitPlatform()
}
