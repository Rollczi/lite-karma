import com.google.devtools.ksp.gradle.KspTask

plugins {
    kotlin("jvm") version "1.8.0"
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("xyz.jpenilla.run-paper") version "2.1.0"
}

version = "1.0.0"

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.codemc.org/repository/maven-releases/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repository.minecodes.pl/releases/")
}

dependencies {
    compileOnly("me.clip:placeholderapi:2.11.3")

    ksp("com.dzikoysk.sqiffy:sqiffy-symbol-processor:1.0.0-alpha.23")
    implementation("com.dzikoysk.sqiffy:sqiffy:1.0.0-alpha.23")

    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    api("net.dzikoysk:cdn:1.14.4")
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.slf4j:slf4j-simple:2.0.6")

    testImplementation("org.testcontainers:mysql:1.18.1")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    testImplementation("org.testcontainers:testcontainers:1.18.1")
    testImplementation("org.testcontainers:junit-jupiter:1.18.1")
}


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("LiteKarma v${project.version}.jar")

    exclude(
        "org/intellij/**",
        "org/jetbrains/**",
    )

    val prefix = "dev.rollczi.litekarma.libs"

    relocate("panda", "$prefix.org.panda")
    relocate("org.panda_lang", "$prefix.org.panda")
    relocate("net.dzikoysk", "$prefix.net.dzikoysk")
    relocate("com.dzikoysk", "$prefix.net.dzikoysk")
    relocate("net.kyori", "$prefix.net.kyori")
    relocate("com.google.gson", "$prefix.com.google.gson")
    relocate("com.fasterxml.jackson", "$prefix.com.fasterxml.jackson")
    relocate("org.slf4j", "$prefix.org.slf4j")
    relocate("io.leangen", "$prefix.io.leangen")
    relocate("google.protobuf", "$prefix.google.protobuf")
}

// MC

tasks {
    runServer {
        minecraftVersion("1.19.4")
    }
}

bukkit {
    main = "dev.rollczi.litekarma.LiteKarmaPlugin"
    apiVersion = "1.13"
    prefix = "LiteKarma"
    author = "Rollczi"
    name = "LiteKarma"
    softDepend = listOf("PlaceholderAPI")
    version = "${project.version}"
}

// TESTS

tasks.test {
    useJUnitPlatform()
}

// KSP

sourceSets.configureEach {
    kotlin.srcDir("$buildDir/generated/ksp/$name/kotlin/")
}

tasks.withType<KspTask> {
    dependsOn("clean")
}