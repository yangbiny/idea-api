package com.reason.plugin.infra

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.psi.PsiFile

/**
 * @author impassive
 */
data class PsiContainer(
    val psiFile: PsiFile,
    val dataContext: DataContext
)