package com.reason.support.adapter

import com.intellij.psi.PsiFile
import com.reason.support.common.ExportItem

/**
 * @author impassive
 */
interface LanguageResolver {

    fun resolve(psiFile: PsiFile): List<ExportItem>

}