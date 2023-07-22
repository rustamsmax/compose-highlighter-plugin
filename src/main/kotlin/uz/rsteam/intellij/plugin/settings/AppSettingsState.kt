package uz.rsteam.intellij.plugin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "uz.rsteam.intellij.plugin.settings.AppSettingsState",
    storages = [Storage("SdkSettingsPlugin.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState?> {

    @JvmField
    var annotationsFqn = "uz.rsteam.analytics.compose.highlighter"

    override fun getState(): AppSettingsState? = this

    override fun loadState(state: AppSettingsState) = XmlSerializerUtil.copyBean(state, this)
}