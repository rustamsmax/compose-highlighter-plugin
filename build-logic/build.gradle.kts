plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  google()
  gradlePluginPortal()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
  implementation(libs.kotlin.intellij.plugin)
  implementation(libs.kotlin.gradle.plugin)
  implementation(libs.jetbrains.compose.plugin)

  compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

}