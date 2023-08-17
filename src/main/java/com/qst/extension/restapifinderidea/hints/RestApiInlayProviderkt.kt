package com.qst.extension.restapifinderidea.hints

import com.intellij.codeInsight.ExternalAnnotationsManager
import com.intellij.codeInsight.InferredAnnotationsManager
import com.intellij.codeInsight.hints.*
import com.intellij.codeInsight.hints.presentation.InlayPresentation
import com.intellij.codeInsight.hints.presentation.MenuOnClickPresentation
import com.intellij.codeInsight.hints.presentation.SequencePresentation
import com.intellij.codeInsight.javadoc.JavaDocInfoGenerator
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.BlockInlayPriority
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.util.Key
import com.intellij.psi.*
import javax.swing.JPanel
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.SmartList
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.openapi.module.ModuleManager
import com.intellij.psi.util.PsiTreeUtil
import com.qst.extension.restapifinderidea.model.RestApiModel
import com.qst.extension.restapifinderidea.utils.MappingAnnUtil.Companion.parseApi
import com.qst.extension.restapifinderidea.utils.RestApiUtil.parseUsage

class RestApiInlayProviderkt:InlayHintsProvider<RestApiInlayProviderkt.Settings> {

    data class Settings(var showApiHints:Boolean = true)
    companion object {
        val ourkey:SettingsKey<Settings> = SettingsKey("RestApi.value.hints")
    }
    override val key: SettingsKey<Settings>
        get() = ourkey
    override val name: String
        get() = "RestApiInlayProviderkt"
    override val previewText: String?
        get() = null

    override fun createSettings(): Settings  = Settings()

    override fun getCollectorFor(file: PsiFile, editor: Editor, settings: Settings, sink: InlayHintsSink): InlayHintsCollector? {

        val project = file.project
        val document = PsiDocumentManager.getInstance(project).getDocument(file)
        val path = file.virtualFile.path
        val modules = ModuleManager.getInstance(project).modules
        var moduleName = ""
        modules.forEach {
            val moduleFolderPath = it.moduleFilePath.replace(it.name + ".iml","")
            if(path.contains(moduleFolderPath)) {
                moduleName = it.name.split("-").last()
            }
        }

        println(path)
        println(moduleName)
        return object: FactoryInlayHintsCollector(editor) {

            override fun collect(element: PsiElement, editor: Editor, sink: InlayHintsSink): Boolean {
                if(DumbService.isDumb(project)) return false
                if(file.project.isDefault) return  false
                if(element is PsiModifierListOwner && element is PsiMethod) {
                    val mapperAnn = element.annotations.find { it.qualifiedName?.contains("Mapping") == true }
                    if(mapperAnn != null) {
                        val restApi = parseApi(element, moduleName)
                        if (restApi != null) {
                            val presentation = annotationPresentation(restApi)
                            val modifierList = element.modifierList
                            if (modifierList != null && document != null) {
                                val textRange = modifierList.textRange ?: return true
                                val offset = textRange.startOffset
                                val width = EditorUtil.getPlainSpaceWidth(editor)
                                val line = document.getLineNumber(offset)
                                val startOffset = document.getLineStartOffset(line)
                                val column = offset - startOffset
                                val shifted = factory.inset(presentation, left = column * width)
                                sink.addBlockElement(offset, true, true, BlockInlayPriority.ANNOTATIONS, shifted)
                            }
                        }
                    }
                }
                return true
            }


            private fun annotationPresentation(restApi: RestApiModel):InlayPresentation = with(factory) {
                val usageInfo = parseUsage(restApi)
                    return@with text(usageInfo)
            }

        }
    }
    override fun createConfigurable(settings: Settings): ImmediateConfigurable = object:ImmediateConfigurable {

        override fun createComponent(listener: ChangeListener): JPanel = panel{}
    }

}