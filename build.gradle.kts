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
    // database
    // - postgres driver
    implementation("org.postgresql:postgresql:42.7.8")
    // - Exposed Database Library (ORM, Query Builder, Migration)
    implementation("org.jetbrains.exposed:exposed-migration-core:1.0.0-rc-2")
    implementation("org.jetbrains.exposed:exposed-migration-jdbc:1.0.0-rc-2")
    implementation("org.jetbrains.exposed:exposed-core:1.0.0-rc-2")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0-rc-2")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:1.0.0-rc-2")
    // tests
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("net.datafaker:datafaker:2.5.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}