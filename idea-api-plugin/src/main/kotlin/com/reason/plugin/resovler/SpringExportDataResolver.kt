package com.reason.plugin.resovler

import com.google.inject.Singleton
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.AllClassesSearch
import com.reason.plugin.common.ExportData
import com.reason.plugin.common.ExportItem
import com.reason.plugin.common.HttpMethod
import com.reason.plugin.common.MethodParamInfo
import com.reason.plugin.infra.PsiContainer
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.getReturnTypeReference
import org.jetbrains.kotlin.psi.*

/**
 * @author impassive
 */
@Singleton
open class SpringExportDataResolver : ExportDataResolver {
    override fun resolvePsiClassData(psiContainer: PsiContainer): ExportData {
        val ktFile = psiContainer.psiFile as KtFile
        var exportNameName = ""
        var baseRequestMapping = ""

        val items = mutableListOf<ExportItem>()
        val declarations = ktFile.declarations
        for (ktClass in declarations) {

            if (ktClass is KtClass) {

                exportNameName = ktClass.name.orEmpty()
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

                    val params = mutableListOf<MethodParamInfo>()
                    if (method is KtFunction) {
                        val valueParameterList = method.valueParameters
                        val returnTypeReference = method.getReturnTypeReference()
                        val ktTypeReference =
                            returnTypeReference?.typeElement?.typeArgumentsAsTypes?.get(0)
                        val packageName =
                            ((returnTypeReference?.typeElement as KtUserType).referenceExpression?.containingFile as KtFile).packageFqName.asString()

                        for (ktParameter in valueParameterList) {
                            val annotationEntries = ktParameter.annotationEntries
                            for (annotationEntry in annotationEntries) {
                                val paramName = annotationEntry.text.orEmpty()
                                println(paramName)
                            }
                            // 参数的名称w
                            val paramName = ktParameter.name
                            // 参数的类型的名称
                            val paramTypeName = ktParameter.typeReference?.text.orEmpty()
                            // 参数类型的class信息
                            val paramTypeClass = find(method.project, paramTypeName)
                            params.add(MethodParamInfo(paramName.orEmpty(), paramTypeClass!!))
                        }
                    }

                    var path = ""
                    var httpMethod: HttpMethod = HttpMethod.NO_METHOD
                    for (methodAnno in method.annotationEntries) {
                        for (valueArgument in methodAnno.valueArguments) {
                            path = valueArgument.getArgumentExpression()?.text.orEmpty()
                                .replace("\"", "")
                            httpMethod = buildMethod(
                                methodAnno.shortName.toString(),
                                methodAnno.valueArguments
                            )
                        }
                    }

                    val exportItem = ExportItem(
                        requestUrl = baseRequestMapping + path,
                        method = httpMethod,
                        requestParams = params
                    )
                    items.add(exportItem)
                }
            }
        }

        return ExportData(
            exportName = exportNameName,
            items = items
        )
    }

    private fun find(project: Project, className: String): PsiClass? {
        val search = AllClassesSearch.search(GlobalSearchScope.projectScope(project), project)
        return search.first {
            val name = it.name
            if (name == className) {
                // 获取类上的注解
                it.annotations[0].findDeclaredAttributeValue("value")?.text

                // 获取每一个字段的注解
                it.allFields[0].name
                // 获取对应注解的信息
                it.allFields[0].annotations[0].findDeclaredAttributeValue("message")?.text
            }
            name == className
        }
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
        println(requestAnnoName + " " + valueArguments.size)
        return HttpMethod.POST
    }
}