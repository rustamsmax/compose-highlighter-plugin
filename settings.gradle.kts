pluginManagement {
  repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  plugins {
    id("de.fayard.refreshVersions") version "0.60.3"
  }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}

plugins {
  id("de.fayard.refreshVersions")
}

rootProject.name = "highlighting"

includeBuild("build-logic")
