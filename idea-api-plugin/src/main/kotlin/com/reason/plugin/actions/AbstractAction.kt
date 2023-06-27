package com.reason.plugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiJavaFile
import org.slf4j.LoggerFactory

/**
 * @author impassive
 */
abstract class AbstractAction : AnAction() {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val data = actionEvent.getData(CommonDataKeys.EDITOR)

        val id = actionEvent.actionManager.getId(this)
        log.info("start : $id")

        val project = actionEvent.project
        val psiFile = PsiDocumentManager.getInstance(project!!).getPsiFile(data!!.document)
        val psiJavaFile = psiFile as PsiJavaFile

        val classes = psiJavaFile.classes
        for (psiClass in classes) {
            export(psiClass)
        }
    }

    private fun export(psiClass: PsiClass) {
        doExport(psiClass)
    }

    abstract fun doExport(psiClass: PsiClass): Boolean

    companion object {
        private val log = LoggerFactory.getLogger(AbstractAction::class.java)
    }
}