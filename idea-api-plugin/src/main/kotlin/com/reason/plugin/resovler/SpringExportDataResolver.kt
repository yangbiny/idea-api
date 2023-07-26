package com.reason.plugin.resovler

import com.google.inject.Singleton
import com.intellij.psi.PsiClass
import com.reason.plugin.common.ExportData
import com.reason.plugin.common.ExportItem
import com.reason.plugin.common.utils.SPIUtils
import com.reason.plugin.infra.PsiContainer
import com.reason.plugin.resovler.items.KotlinClassResolver
import org.jetbrains.kotlin.psi.KtFile

/**
 * @author impassive
 */
@Singleton
open class SpringExportDataResolver : ExportDataResolver {
    override fun resolvePsiClassData(psiContainer: PsiContainer): ExportData {
        val psiFile = psiContainer.psiFile
        val exportNameName = psiFile.name
        val items = mutableListOf<ExportItem>()

        val classes = if (psiFile is KtFile) {
            psiFile.declarations.filter { it is PsiClass }
        } else emptyList()

        classes.forEach {
            SPIUtils.resolveClass(it.language, LanguageResolver::class.java)
        }

        return ExportData(
            exportName = exportNameName,
            items = items
        )
    }

}