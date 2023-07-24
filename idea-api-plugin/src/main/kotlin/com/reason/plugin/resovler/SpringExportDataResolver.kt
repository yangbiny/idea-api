package com.reason.plugin.resovler

import com.google.inject.Singleton
import com.reason.plugin.common.ExportData
import com.reason.plugin.common.ExportItem
import com.reason.plugin.common.HttpMethod
import com.reason.plugin.infra.PsiContainer
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.ValueArgument

/**
 * @author impassive
 */
@Singleton
open class SpringExportDataResolver : ExportDataResolver {
    override fun resolvePsiClassData(psiContainer: PsiContainer): ExportData {
        val ktFile = psiContainer.psiFile as KtFile

        val exportNameName = ktFile.name

        var baseRequestMapping = ""

        val items = mutableListOf<ExportItem>()
        val declarations = ktFile.declarations
        for (ktClass in declarations) {

            if (ktClass is KtClass) {

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

                    if (method is KtFunction) {
                        val valueParameterList = method.valueParameters
                        for (ktParameter in valueParameterList) {
                            val annotationEntries = ktParameter.annotationEntries
                            for (annotationEntry in annotationEntries) {
                                val paramName = annotationEntry.text.orEmpty()
                                println(paramName)
                            }
                            val paramName = ktParameter.name
                            val paramType = ktParameter.typeReference?.text.orEmpty()
                        }
                    }

                    for (methodAnno in method.annotationEntries) {
                        for (valueArgument in methodAnno.valueArguments) {
                            val path = valueArgument.getArgumentExpression()?.text.orEmpty()
                                .replace("\"", "")
                            val exportItem = ExportItem(
                                requestUrl = baseRequestMapping + path,
                                method = buildMethod(
                                    methodAnno.shortName.toString(),
                                    methodAnno.valueArguments
                                )
                            )
                            items.add(exportItem)
                        }
                    }
                }
            }
        }

        return ExportData(
            exportName = exportNameName,
            items = items
        )
    }

    private fun buildMethod(
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
        return HttpMethod.POST
    }
}