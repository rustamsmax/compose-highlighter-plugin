rootProject.name = "highlighting"

pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
  }
}
dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}
includeBuild("build-logic")
