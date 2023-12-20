plugins {
    id("uz.rsteam.highlighting")
    id("org.jetbrains.intellij") version "1.16.1" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.21" apply false
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

/*tasks.compileKotlin {
    kotlinOptions.jvmTarget = "17"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "17"
}

tasks.buildSearchableOptions {
    enabled = false
}*/
