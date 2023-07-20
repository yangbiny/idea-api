package com.reason.plugin.resovler

import com.google.inject.Singleton
import com.intellij.psi.PsiModifierListOwner
import com.reason.plugin.common.ExportData
import com.reason.plugin.infra.PsiContainer
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile

/**
 * @author impassive
 */
@Singleton
open class SpringExportDataResolver : ExportDataResolver {
    override fun resolvePsiClassData(psiContainer: PsiContainer): ExportData {
        val ktFile = psiContainer.psiFile as KtFile
        val exportNameName = ktFile.name
        val annotations = ktFile.annotations

        (ktFile.children[4] as KtClass).annotationEntries[0]

        for (child in ktFile.children) {
            if (child is KtAnnotationEntry) {
                val psiModifierListOwner = child.parent as? PsiModifierListOwner
                if (psiModifierListOwner != null) {
                    println(psiModifierListOwner)
                }
            }
        }

        val baseRequestMapping = ""
        for (annotation in annotations) {
            println(annotation)
        }
        return ExportData(
            exportName = exportNameName,
            items = emptyList()
        )
    }
}