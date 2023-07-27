package com.reason.plugin.resovler.items

import com.intellij.psi.PsiFile
import com.intellij.psi.impl.compiled.ClsClassImpl
import com.reason.plugin.common.ExportItem
import com.reason.plugin.common.HttpMethod
import com.reason.plugin.common.MethodParamInfo
import com.reason.plugin.common.utils.PsiUtils
import com.reason.plugin.resovler.AbstractLanguageResolver
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.getReturnTypeReference
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtUserType

/**
 * @author impassive
 */
open class KotlinClassResolver : AbstractLanguageResolver() {
    override fun doResolve(psiFile: PsiFile): List<ExportItem> {
        if (psiFile !is KtFile) {
            return emptyList()
        }
        val ktClassList = psiFile.declarations.filterIsInstance<KtClass>()
        if (ktClassList.isEmpty()) {
            return emptyList()
        }

        val ktClass = ktClassList[0]

        val exportNameName = ktClass.name.orEmpty()
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

        val items = mutableListOf<ExportItem>()
        for (method in ktClass.declarations) {

            val params = mutableListOf<MethodParamInfo>()
            if (method is KtFunction) {
                val valueParameterList = method.valueParameters
                if (method.hasDeclaredReturnType()) {
                    val returnTypeReference = method.getReturnTypeReference()!!
                    val returnType =
                        returnTypeReference.typeElement?.typeArgumentsAsTypes?.get(0)
                    val returnTypePackageName =
                        returnTypeReference.containingKtFile.packageFqName.asString()
                    val returnTypeOfOut =
                        ((returnTypeReference.typeElement as KtUserType).referenceExpression!!.references[0].resolve() as ClsClassImpl).qualifiedName
                }

                for (ktParameter in valueParameterList) {
                    val annotationEntries = ktParameter.annotationEntries
                    for (annotationEntry in annotationEntries) {
                        val paramName = annotationEntry.text.orEmpty()
                        println(paramName)
                    }
                    // 参数的名称w
                    val paramName = ktParameter.name
                    // 参数的类型的名称
                    val paramTypeName =
                        ktParameter.typeReference?.text.orEmpty()
                    val packageName =
                        ktParameter.containingKtFile.packageFqName.asString()
                    // 参数类型的class信息
                    val paramTypeClass =
                        PsiUtils.findPisClassByName(
                            "$packageName.$paramTypeName",
                            method.project
                        )
                    params.add(
                        MethodParamInfo(
                            paramName.orEmpty(),
                            paramTypeClass!!
                        )
                    )
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
        return items
    }
}