plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "8.3.2"
    `maven-publish`
}

val minestomVersion: String by extra { "f71ab6d851" }

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "kotlin")

    kotlin {
        jvmToolchain(21)
    }

    tasks.register<BuildAndPublishTask>("buildAndPublish") {
        group = "build"
        description = "Build and publish to Maven Local"

        dependsOn("build", "publishToMavenLocal")
    }
}

val server = project("server")

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.gradleup.shadow")

    val jarName = "${project.name}.jar"

    tasks.register<Copy>("moveJar") {
        val sourceDir = file("${layout.buildDirectory.asFile.get().path}/libs")
        var destinationDir = file("../dev-env/")

        if (project != server)
            destinationDir = destinationDir.resolve("plugins/")

        destinationDir.mkdirs()

        from(sourceDir) {
            include { details ->
                details.file.name == jarName
            }
        }

        into(destinationDir)
    }

    tasks.shadowJar {
        archiveFileName.set(jarName)
        finalizedBy(tasks.named("moveJar"))
    }

    publishing {
        afterEvaluate {
            publications {
                create<MavenPublication>("maven") {
                    groupId = "gg.airbrush"
                    artifactId = project.name
                    version = "${project.version}"

                    from(components["java"])
                }
            }
        }
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }
}

abstract class BuildAndPublishTask : DefaultTask() {
    @TaskAction
    fun action() {}
}