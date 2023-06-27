package com.reason.plugin.actions

import com.google.inject.Inject
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiJavaFile
import com.reason.plugin.actions.AbstractAction
import com.reason.plugin.export.DataExporter
import com.reason.plugin.resovler.ExportDataResolver

/**
 * @author impassive
 */
open class YapiExportAction : AbstractAction() {

    @Inject
    private lateinit var exportDataResolver: ExportDataResolver

    @Inject
    private lateinit var dataExporter: DataExporter
    override fun doExport(psiClass: PsiClass): Boolean {

        val annotations = psiClass.annotations
        // 根据 注解，选择 不同的解析器
        //1. 解析 为 指定的对象
        val exportData = exportDataResolver.resolvePsiClassData(psiClass)
        //2. 导出 数据
        dataExporter.export(exportData)
        return false
    }


}