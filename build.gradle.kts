plugins {
    id("uz.rsteam.highlighting")
//    id("org.jetbrains.kotlin.jvm") version "1.9.21" apply false
    kotlin("jvm") version "1.9.21"
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

sourceSets.main.configure {
    java.srcDirs("src/main/gen")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")
    testImplementation("junit:junit:4.13.2")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.buildSearchableOptions {
    enabled = false
}
kotlin {
    jvmToolchain(17)
}