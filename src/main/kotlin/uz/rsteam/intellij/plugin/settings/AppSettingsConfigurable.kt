package uz.rsteam.intellij.plugin.settings

import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class AppSettingsConfigurable : Configurable {

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    private var mySettingsComponent: AppSettingsComponent? = null

    private val settings
        get() = service<AppSettingsState>()

    override fun getDisplayName(): String = "Multiplatform Compose Highlighter"

    override fun getPreferredFocusedComponent(): JComponent {
        return mySettingsComponent!!.preferredFocusedComponent
    }

    override fun createComponent(): JComponent? {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        return mySettingsComponent!!.annotationsFQN != settings.annotationsFqn
    }

    override fun apply() {
        settings.annotationsFqn = mySettingsComponent!!.annotationsFQN
    }

    override fun reset() {
        mySettingsComponent!!.annotationsFQN = settings.annotationsFqn
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}
