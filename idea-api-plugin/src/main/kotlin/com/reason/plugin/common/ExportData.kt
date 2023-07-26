package com.reason.plugin.common

import com.intellij.psi.PsiClass

/**
 * @author impassive
 */
data class ExportData(
    val exportName: String,
    val items: List<ExportItem>
)

data class ExportItem(
    // 请求方法
    val method: HttpMethod,
    // 请求URL
    val requestUrl: String,
    // 请求头信息（包括 请求的参数类型: 例如：ContentType: application/json）
    val requestHeaders: Map<String, String> = emptyMap(),
    // 请求参数名称及其类型：name:String
    val requestParams: List<MethodParamInfo>,
    // 请求信息体：参数及其类型：name:
    val requestBody: Any? = null,
    val responseBody: MethodReturnInfo? = null
)

data class MethodParamInfo(
    val name: String,
    val type: PsiClass
)

data class MethodReturnInfo(
    val outType: PsiClass?,
    val innerType: PsiClass?,
)