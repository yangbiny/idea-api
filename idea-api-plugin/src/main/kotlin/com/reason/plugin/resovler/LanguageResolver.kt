package com.reason.plugin.resovler

import com.intellij.psi.PsiClass
import com.reason.plugin.common.ExportItem

/**
 * @author impassive
 */
interface LanguageResolver {

    fun resolve(psiClass: PsiClass): List<ExportItem>

}