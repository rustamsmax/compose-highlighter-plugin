package uz.rsteam.intellij.plugin.settings

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

/**
 * Supports creating and managing a JPanel for the Settings Dialog.
 */
class AppSettingsComponent {

    private val annotationsFQNText = JBTextArea()

    val preferredFocusedComponent = annotationsFQNText

    var annotationsFQN: String
        get() = annotationsFQNText.text
        set(newText) {
            annotationsFQNText.text = newText
        }

    val panel: JPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(JPanel(VerticalLayout(1)).apply {
            add(JBLabel("Add fully qualified names of Java/Kotlin Annotations that should be lowlighted."))
            add(JBLabel("Separate multiple annotations by newlines."))
        }, annotationsFQNText, 1, true)
        .addComponentFillVertically(JPanel(), 0)
        .panel
}