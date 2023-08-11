package com.reason.plugin.resovler

import com.intellij.psi.PsiFile
import com.reason.support.adapter.LanguageResolver
import com.reason.support.common.ExportItem

/**
 * @author impassive
 */
abstract class AbstractLanguageResolver : LanguageResolver {

    override fun resolve(psiFile: PsiFile): List<ExportItem> {
        return doResolve(psiFile)
    }

    abstract fun doResolve(psiFile: PsiFile): List<ExportItem>

}