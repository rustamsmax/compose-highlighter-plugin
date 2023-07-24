package uz.rsteam

import gradle.kotlin.dsl.accessors._63ad9e7510e1c50cc906ef277cc2427c.buildSearchableOptions
import gradle.kotlin.dsl.accessors._63ad9e7510e1c50cc906ef277cc2427c.intellij
import gradle.kotlin.dsl.accessors._63ad9e7510e1c50cc906ef277cc2427c.patchPluginXml
import gradle.kotlin.dsl.accessors._63ad9e7510e1c50cc906ef277cc2427c.publishPlugin
import org.gradle.kotlin.dsl.java
import uz.rsteam.highlighting.pluginChangeNotes
import java.io.FileInputStream
import java.util.*

plugins {
    java
    id("org.jetbrains.intellij")
}

group = "uz.rsteam.intellij.plugin"
version = "1.0.1"

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2022.1")
    plugins.set(listOf("java", "Kotlin"))
    type.set("IC")
}

tasks.buildSearchableOptions {
    enabled = false
}

tasks.patchPluginXml {
    version.set(project.version.toString())
    sinceBuild.set("211")
    untilBuild.set("231.*")
    pluginDescription.set(uz.rsteam.highlighting.description)
    changeNotes.set(pluginChangeNotes.toHtml())
}

val propertiesFile = rootProject.file("local.properties")
val highlightingToken = if (propertiesFile.exists()) {
    val localProperties = Properties().apply {
        load(FileInputStream(propertiesFile))
    }
    localProperties.getProperty("org.gradle.project.intellijPublishToken")
} else {
    null
}

tasks.publishPlugin {
    token.set(highlightingToken)
}
