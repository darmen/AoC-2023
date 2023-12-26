plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    jcenter()
}

dependencies {
    implementation("io.github.p-org.solvers:z3:4.8.14-v5")
    implementation("net.sf.jung:jung-algorithms:2.1.1")
    implementation("net.sf.jung:jung-graph-impl:2.1.1")
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
