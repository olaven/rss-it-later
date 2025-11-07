plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    application
}

group = "org.olaven.rssitlater"
version = "1.0-SNAPSHOT"

application {
    mainClass = "org.olaven.rssitlater.MainKt"
}



tasks.register<Exec>("dev") {
    commandLine("watchexec", "-r", "-w", "src", "--", "./gradlew", "run")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.7.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("io.github.cdimascio:dotenv-kotlin:6.5.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
    implementation("com.chimbori.crux:crux:5.1.0")
    // serialization
    implementation("org.redundent:kotlin-xml-builder:1.9.2")
    // website HTML DSL
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.12.0")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:2025.11.4")
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
    testImplementation("io.mockk:mockk:1.14.6")
    testImplementation("io.javalin:javalin-bundle:6.7.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}