package com.reason.plugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.impl.EditorComponentImpl
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiClass

/**
 * @author impassive
 */
open class PostmanExportAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // 获取到的是 对应 文件的URL
        val fileUrl =
            ((e.dataContext.getData("contextComponent") as EditorComponentImpl).editor.virtualFile as VirtualFile).presentableUrl

        val data = e.getData(CommonDataKeys.PSI_ELEMENT)

        val contextClassLoader = Thread.currentThread().contextClassLoader
        val loadClass = contextClassLoader.loadClass(fileUrl)

        val methods = loadClass.methods

        for (method in methods) {
            val annotations = method.annotations
            val parameters = method.parameters
            val returnType = method.returnType
            println(annotations)
        }
    }


}