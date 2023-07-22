plugins {
    id("uz.rsteam.highlighting")
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
}

repositories {
    mavenCentral()
}

sourceSets.main.configure {
    java.srcDirs("src/main/gen")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("junit:junit:4.13.2")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "11"
}

tasks.buildSearchableOptions {
    enabled = false
}
