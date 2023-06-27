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
        TODO("Not yet implemented")
    }
}