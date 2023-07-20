package com.reason.plugin.actions

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiDocumentManager
import com.reason.plugin.export.DataExporter
import com.reason.plugin.export.YapiDataExporter
import com.reason.plugin.infra.ApiContext
import com.reason.plugin.infra.ApiContextUtil
import com.reason.plugin.infra.PsiContainer
import com.reason.plugin.resovler.ExportDataResolver
import com.reason.plugin.resovler.SpringExportDataResolver
import org.jetbrains.kotlin.psi.KtFile
import org.slf4j.LoggerFactory

/**
 * @author impassive
 */
abstract class AbstractAction(text: String) : AnAction(text) {

    private val injector: Injector = Guice.createInjector(GuiceModule())

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val data = actionEvent.getData(CommonDataKeys.EDITOR)
        val project = actionEvent.project
        val psiFile = PsiDocumentManager.getInstance(project!!).getPsiFile(data!!.document)!!

        val psiContainer = PsiContainer(psiFile)
        ApiContextUtil.set(
            ApiContext(
                injector = injector,
                psiContainer = psiContainer
            )
        )

        export(psiContainer)
    }

    private fun export(psiContainer: PsiContainer) {
        try {
            before()
            doExport(psiContainer)
        } finally {
            after()
        }

    }

    private fun after() {
        ApiContextUtil.clean()
    }

    private fun before() {
        doBefore()
    }

    abstract fun doBefore()

    abstract fun doExport(psiContainer: PsiContainer): Boolean

    companion object {
        private val log = LoggerFactory.getLogger(AbstractAction::class.java)
    }
}

open class GuiceModule : AbstractModule() {

    override fun configure() {
        bind(DataExporter::class.java).to(YapiDataExporter::class.java)
        bind(ExportDataResolver::class.java).to(SpringExportDataResolver::class.java)
    }
}