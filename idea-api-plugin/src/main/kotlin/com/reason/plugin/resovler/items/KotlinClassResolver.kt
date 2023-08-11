package com.reason.plugin.resovler.items

import com.intellij.psi.PsiFile
import com.reason.api.support.resolver.KotlinResolver
import com.reason.plugin.resovler.AbstractLanguageResolver
import com.reason.support.common.ExportItem
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile

/**
 * @author impassive
 */
open class KotlinClassResolver : AbstractLanguageResolver() {

    private val resolverAdapter = KotlinResolver()

    override fun doResolve(psiFile: PsiFile): List<ExportItem> {
        if (psiFile !is KtFile) {
            return emptyList()
        }
        val ktClassList = psiFile.declarations.filterIsInstance<KtClass>()
        if (ktClassList.isEmpty()) {
            return emptyList()
        }
        val ktClasses = ktClassList.filter { ktClass ->
            ktClass.annotationEntries.any {
                it.shortName?.asString() == "RestController"
            }
        }
        if (ktClasses.isEmpty()) {
            return emptyList()
        }

        val items = mutableListOf<ExportItem>()
        for (ktClass in ktClasses) {
            var baseRequestMapping = ""
            // 获取 class 顶部的 注解
            for (anno in ktClass.annotationEntries) {
                if (anno.valueArguments.isEmpty()) {
                    continue
                }
                baseRequestMapping =
                    anno.valueArguments[0].getArgumentExpression()?.text.orEmpty()
                        .replace("\"", "")
            }
            for (method in ktClass.declarations) {

                val resolveMethod = resolverAdapter.resolveMethod(method)
                val resolveParam = resolverAdapter.resolveParam(method)
                val resolveReturn = resolverAdapter.resolveReturn(method)

                val exportItem = ExportItem(
                    baseRequestMapping = baseRequestMapping,
                    requestInfo = resolveMethod,
                    requestMethodParam = resolveParam,
                    requestReturnInfo = resolveReturn,
                )
                items.add(exportItem)
            }
        }
        return items
    }
}