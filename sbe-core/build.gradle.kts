/*
 * Copyright (c) 2023 Adaptive Financial Consulting
 */

plugins {
    application
    checkstyle
}

repositories {
    mavenCentral()
}

dependencies {
    checkstyle(libs.checkstyle)
    implementation(libs.agrona)
    implementation(libs.slf4j)
    implementation(libs.logback)
    implementation(project(":sbe-protocol"))
    testImplementation(libs.bundles.testing)
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter(libs.versions.junitVersion.get())
        }
    }
}

