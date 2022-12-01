plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"

    application
}

group = "io.tohuwabohu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
}

application {
    mainClass.set("io.tohuwabohu.aoc2022.MainKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

