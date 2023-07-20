package com.reason.plugin.resovler

import com.reason.plugin.common.ExportData
import com.reason.plugin.infra.PsiContainer

/**
 * @author impassive
 */
interface ExportDataResolver {

    fun resolvePsiClassData(psiContainer: PsiContainer): ExportData
}