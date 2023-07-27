package com.reason.plugin.resovler

import com.intellij.psi.PsiFile
import com.reason.plugin.common.ExportItem

/**
 * @author impassive
 */
interface LanguageResolver {

    fun resolve(psiFile: PsiFile): List<ExportItem>

}