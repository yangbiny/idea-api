package com.reason.api.support.resolver

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.compiled.ClsClassImpl
import com.reason.support.adapter.ResolverAdapter
import com.reason.support.common.HttpMethod
import com.reason.support.common.MethodParam
import com.reason.support.common.MethodRequestInfo
import com.reason.support.common.MethodReturnInfo
import com.reason.support.utils.PsiUtils
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.getReturnTypeReference
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtUserType

/**
 * @author impassive
 */
open class KotlinResolver : ResolverAdapter {
    override fun resolveParam(psiElement: PsiElement): List<MethodParam> {
        val ktFunction = psiElement as KtFunction
        val valueParameterList = ktFunction.valueParameters

        val params = mutableListOf<MethodParam>()
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
                    psiElement.project
                )
            params.add(
                MethodParam(
                    name = paramName.orEmpty(),
                    type = paramTypeClass!!.text,
                    valueCase = "",
                    remark = ""
                )
            )
        }
        return params
    }

    override fun resolveReturn(psiElement: PsiElement): MethodReturnInfo {
        val ktFunction = psiElement as KtFunction

        if (ktFunction.hasDeclaredReturnType()) {
            return MethodReturnInfo.DEFAULT
        }
        val returnTypeReference = ktFunction.getReturnTypeReference()!!
        val returnType =
            returnTypeReference.typeElement?.typeArgumentsAsTypes?.get(0)
        val returnTypePackageName =
            returnTypeReference.containingKtFile.packageFqName.asString()

        val returnTypeOfOut =
            ((returnTypeReference.typeElement as KtUserType).referenceExpression!!.references[0].resolve() as ClsClassImpl).qualifiedName
        println(returnTypeOfOut)
        println(returnTypePackageName)
        println(returnType)
        return MethodReturnInfo(
            name = "",
            type = returnTypeOfOut ?: "",
            remark = "",
            valueCase = "",
            inner = null
        )
    }

    override fun resolveMethod(psiElement: PsiElement): MethodRequestInfo {
        val method = psiElement as KtFunction
        var httpMethod: HttpMethod = HttpMethod.NO_METHOD
        var path = ""
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
        return MethodRequestInfo(
            name = method.name.orEmpty(),
            requestUrl = path,
            httpMethod = httpMethod
        )
    }

}