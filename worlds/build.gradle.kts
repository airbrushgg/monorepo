plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
    id("com.palantir.git-version") version "3.0.0"
}

group = "gg.airbrush"
version = "0.1.0"

val minestomVersion: String by rootProject.extra

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    compileOnly(project(":server"))
    compileOnly("dev.flavored:bamboo:1.1.0")
    compileOnly("net.minestom:minestom-snapshots:$minestomVersion")
    compileOnly("dev.hollowcube:polar:1.11.2")
    compileOnly("cc.ekblad:4koma:1.2.0")
}