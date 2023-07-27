package com.reason.plugin.resovler

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.reason.plugin.common.ExportItem
import com.reason.plugin.common.HttpMethod
import org.jetbrains.kotlin.psi.ValueArgument

/**
 * @author impassive
 */
abstract class AbstractLanguageResolver : LanguageResolver {

    override fun resolve(psiFile: PsiFile): List<ExportItem> {
        return doResolve(psiFile)
    }

    abstract fun doResolve(psiFile: PsiFile): List<ExportItem>


    protected fun buildMethod(
        requestAnnoName: String,
        valueArguments: MutableList<out ValueArgument>
    ): HttpMethod {
        return when (requestAnnoName) {
            "PostMapping" -> HttpMethod.POST
            "GetMapping" -> HttpMethod.GET
            "PutMapping" -> HttpMethod.PUT
            "DeleteMapping" -> HttpMethod.DELETE
            "PatchMapping" -> HttpMethod.PATCH
            "RequestMapping" -> checkAndBuildHttpMethod(requestAnnoName, valueArguments)
            else -> throw IllegalArgumentException("不支持的请求类型")
        }
    }

    private fun checkAndBuildHttpMethod(
        requestAnnoName: String,
        valueArguments: MutableList<out ValueArgument>
    ): HttpMethod {
        println(requestAnnoName + " " + valueArguments.size)
        return HttpMethod.POST
    }
}