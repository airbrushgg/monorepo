import org.gradle.api.tasks.Copy

plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
    id("com.palantir.git-version") version "3.0.0"
    application
}

group = "gg.airbrush"
version = "0.3.2"

val workaroundVersion = version as String
val minestomVersion: String by rootProject.extra

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("net.minestom:minestom-snapshots:$minestomVersion")
    implementation("dev.hollowcube:polar:1.11.2")
    implementation("dev.flavored:bamboo:1.1.0")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
    implementation("cc.ekblad:4koma:1.2.0")
    implementation("ch.qos.logback:logback-core:1.5.8")
    implementation("ch.qos.logback:logback-classic:1.5.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.xerial:sqlite-jdbc:3.46.1.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation(kotlin("reflect"))
}

application {
    mainClass.set("gg.airbrush.server.MainKt")
}