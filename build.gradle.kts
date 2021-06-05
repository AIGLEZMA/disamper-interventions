plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    java
    application

    id("org.openjfx.javafxplugin") version "0.0.9"
    id("edu.sc.seis.launch4j") version "2.5.0"
}

group = "me.aiglez.disamper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

javafx {
    modules = listOf("javafx.controls")
}

launch4j {
    mainClassName = "me.aiglez.disamper.interventions.MainKt"
}

dependencies {
    implementation("com.openhtmltopdf:openhtmltopdf-core:1.0.8")
    implementation("com.openhtmltopdf:openhtmltopdf-pdfbox:1.0.8")

    implementation("org.jetbrains.exposed", "exposed-core", "0.31.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.31.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.31.1")
    implementation("org.jetbrains.exposed", "exposed-java-time", "0.31.1")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("org.slf4j:slf4j-nop:1.7.30")

    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("com.jfoenix:jfoenix:9.0.10")

    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-junit"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}