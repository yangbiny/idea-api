package com.reason.plugin.resovler

import com.google.inject.ImplementedBy
import com.intellij.psi.PsiClass
import com.reason.plugin.common.ExportData

/**
 * @author impassive
 */
@ImplementedBy(SpringExportDataResolver::class)
interface ExportDataResolver {

    fun resolvePsiClassData(psiClass: PsiClass): ExportData
}