package uz.rsteam.intellij.plugin

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyzeWithAllCompilerChecks
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeUtils
import org.jetbrains.kotlin.util.OperatorNameConventions
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI

class KMPComposableAnnotator : Annotator {
    companion object TextAttributeRegistry {
        val COMPOSABLE_CALL_TEXT_ATTRIBUTES_KEY: TextAttributesKey
        val COMPOSABLE_CALL_TEXT_ATTRIBUTES_NAME = "ComposableCallTextAttributes"
        private val ANALYSIS_RESULT_KEY = Key<AnalysisResult>(
            "ComposableAnnotator.DidAnnotateKey"
        )
        private val CAN_CONTAIN_COMPOSABLE_KEY = Key<Boolean>(
            "ComposableAnnotator.CanContainComposable"
        )

        init {
            COMPOSABLE_CALL_TEXT_ATTRIBUTES_KEY = TextAttributesKey.createTextAttributesKey(
                COMPOSABLE_CALL_TEXT_ATTRIBUTES_NAME,
                DefaultLanguageHighlighterColors.FUNCTION_CALL)
        }
    }
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        highlight(element, holder)
    }

    private val TAG = "COMPOSABLE ANNOTATION"

    private fun highlight(element: PsiElement, holder: AnnotationHolder) {
        if (element !is KtCallExpression) return
        var analysisResult = holder.currentAnnotationSession.getUserData(
            ANALYSIS_RESULT_KEY
        )
        if (analysisResult == null) {
            val ktFile = element.containingFile as? KtFile ?: return
            analysisResult = ktFile.analyzeWithAllCompilerChecks()
            holder.currentAnnotationSession.putUserData(
                ANALYSIS_RESULT_KEY, analysisResult
            )
        }
        if (analysisResult.isError()) {
            throw ProcessCanceledException(analysisResult.error)
        }
        val shouldStyle = shouldStyleCall(analysisResult.bindingContext, element)
        if (!shouldStyle) return

        val elementToStyle = element.calleeExpression ?: return

        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(elementToStyle)
            .textAttributes(COMPOSABLE_CALL_TEXT_ATTRIBUTES_KEY)
            .create()
    }
    @OptIn(IDEAPluginsCompatibilityAPI::class)
    private fun shouldStyleCall(bindingContext: BindingContext, element: KtCallExpression): Boolean {
        val resolved = element.getResolvedCall(bindingContext)
        return resolved?.isComposableInvocation() == true
    }

    private fun ResolvedCall<*>.isComposableInvocation(): Boolean {
        println("$TAG checking -> ${this.javaClass}")
        if (this is VariableAsFunctionResolvedCall) {
            if (variableCall.candidateDescriptor.type.hasComposableAnnotation())
                return true
            if (functionCall.resultingDescriptor.hasComposableAnnotation()) return true
            return false
        }
        val candidateDescriptor = candidateDescriptor
        if (candidateDescriptor is FunctionDescriptor) {
            if (candidateDescriptor.isOperator &&
                candidateDescriptor.name == OperatorNameConventions.INVOKE
            ) {
                if (dispatchReceiver?.type?.hasComposableAnnotation() == true) {
                    return true
                }
            }
        }
        return when (candidateDescriptor) {
            is ValueParameterDescriptor -> false
            is LocalVariableDescriptor -> false
            is PropertyDescriptor -> {
                val isGetter = valueArguments.isEmpty()
                val getter = candidateDescriptor.getter
                if (isGetter && getter != null) {
                    getter.hasComposableAnnotation()
                } else {
                    false
                }
            }
            is PropertyGetterDescriptor ->
                candidateDescriptor.correspondingProperty.hasComposableAnnotation()
            else -> candidateDescriptor.hasComposableAnnotation()
        }
    }

    private fun KotlinType.hasComposableAnnotation(): Boolean =
        !isSpecialType && annotations.findAnnotation(ComposeFqNames.Composable) != null

    private val KotlinType.isSpecialType: Boolean get() =
        this === TypeUtils.NO_EXPECTED_TYPE || this === TypeUtils.UNIT_EXPECTED_TYPE

    private fun Annotated.hasComposableAnnotation(): Boolean =
        annotations.findAnnotation(ComposeFqNames.Composable) != null
}


private const val root = "androidx.compose.runtime"
private val rootFqName = FqName(root)
@Suppress("SameParameterValue")
object ComposeClassIds {
    private fun classIdFor(cname: String) =
        ClassId(rootFqName, Name.identifier(cname))

    val Composable = classIdFor("Composable")
}


object ComposeFqNames {
    val Composable = ComposeClassIds.Composable.asSingleFqName()
}
