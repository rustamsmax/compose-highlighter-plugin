plugins {
  id("uz.rsteam.highlighting")
  alias(libs.plugins.jetbrains.compose)
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
  implementation(libs.kotlin.stdlib)
  implementation(libs.jetbrains.compose.plugin)
  implementation(compose.ui)
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