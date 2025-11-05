plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.olaven.rssitlater"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.7.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("io.github.cdimascio:dotenv-kotlin:6.5.1")
    testImplementation(kotlin("test"))
    // database
    // - postgres driver
    implementation("org.postgresql:postgresql:42.7.8")
    // - migration tool
    implementation("org.jetbrains.exposed:exposed-migration-core:1.0.0-rc-2")
    implementation("org.jetbrains.exposed:exposed-migration-jdbc:1.0.0-rc-2")
    implementation("org.jetbrains.exposed:exposed-core:1.0.0-rc-2")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0-rc-2")
    // - orm
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}