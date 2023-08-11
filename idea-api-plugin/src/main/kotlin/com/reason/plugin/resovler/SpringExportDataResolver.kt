package com.reason.plugin.resovler

import com.google.inject.Singleton
import com.reason.plugin.common.ExportData
import com.reason.plugin.common.utils.SPIUtils
import com.reason.plugin.infra.PsiContainer
import com.reason.support.adapter.LanguageResolver

/**
 * @author impassive
 */
@Singleton
open class SpringExportDataResolver : ExportDataResolver {
    override fun resolvePsiClassData(psiContainer: PsiContainer): ExportData {
        val psiFile = psiContainer.psiFile
        val exportNameName = psiFile.name
        val resolver = SPIUtils.resolveClass(psiFile.language, LanguageResolver::class.java)
        val exportItems = resolver.resolve(psiFile)
        return ExportData(
            exportName = exportNameName,
            items = exportItems
        )
    }

}