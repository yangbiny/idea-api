package com.reason.plugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiJavaFile

/**
 * @author impassive
 */
open class PostmanExportAction : AnAction() {
    override fun actionPerformed(actionEvent: AnActionEvent) {

        val data = actionEvent.getData(CommonDataKeys.EDITOR)
        val project = actionEvent.project
        val psiFile = PsiDocumentManager.getInstance(project!!).getPsiFile(data!!.document)
        val psiJavaFile = psiFile as PsiJavaFile

        val classes = psiJavaFile.classes
        val psiClass = classes[0]
    }


    private fun exportClass(psiClass: PsiClass) {
        val annotations = psiClass.annotations
        // 检查 是否是 API Class
        val psiMethods = psiClass.methods
        val psiMethod = psiMethods[0]
        // 参数名称
        psiMethod.parameterList.parameters[0].name
        println(psiMethod)

    }


}