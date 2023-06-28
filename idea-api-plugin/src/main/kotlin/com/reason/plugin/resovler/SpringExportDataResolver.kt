package com.reason.plugin.resovler

import com.google.inject.Singleton
import com.intellij.psi.PsiClass
import com.reason.plugin.common.ExportData

/**
 * @author impassive
 */
@Singleton
open class SpringExportDataResolver : ExportDataResolver {
    override fun resolvePsiClassData(psiClass: PsiClass): ExportData {
        val exportNameName = psiClass.docComment?.text ?: psiClass.name
        val annotations = psiClass.annotations

        val baseRequestMapping = ""
        for (annotation in annotations) {
            println(annotation)
        }

        return ExportData(
            exportName = exportNameName!!,
            items = emptyList()
        )
    }
}