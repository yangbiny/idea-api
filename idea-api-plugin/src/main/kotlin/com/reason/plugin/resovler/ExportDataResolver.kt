package com.reason.plugin.resovler

import com.intellij.psi.PsiClass
import com.reason.plugin.common.ExportData

/**
 * @author impassive
 */
interface ExportDataResolver {

    fun resolvePsiClassData(psiClass: PsiClass): ExportData
}