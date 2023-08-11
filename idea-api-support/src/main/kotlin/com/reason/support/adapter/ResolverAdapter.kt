package com.reason.support.adapter

import com.intellij.psi.PsiElement
import com.reason.support.common.HttpMethod
import com.reason.support.common.MethodParam
import com.reason.support.common.MethodRequestInfo
import com.reason.support.common.MethodReturnInfo
import org.jetbrains.kotlin.psi.ValueArgument

/**
 * @author impassive
 */
interface ResolverAdapter {

    /**
     * 解析参数信息
     */
    fun resolveParam(psiElement: PsiElement): List<MethodParam>

    /**
     * 解析返回值信息
     */
    fun resolveReturn(psiElement: PsiElement): MethodReturnInfo

    /**
     * 解析方法的请求信息
     */
    fun resolveMethod(psiElement: PsiElement): MethodRequestInfo

    fun buildMethod(
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