package com.reason.support.common

data class MethodParam(
    // 参数名称
    val name: String,
    // 参数类型
    val type: String,
    // 参数的案例值
    val valueCase: String = "",
    val remark: String,
    // 该参数下的 其他参数
    val params: List<MethodParam> = emptyList()
)


data class MethodReturnInfo(
    val name: String,
    val type: String,
    val valueCase: String = "",
    val remark: String,
    val inner: MethodReturnInfo?
) {

    companion object {
        val DEFAULT = MethodReturnInfo(
            name = "",
            type = "",
            valueCase = "",
            remark = "",
            inner = null
        )
    }
}

data class MethodRequestInfo(
    // 请求的名称
    val name: String,
    // 请求的地址
    val requestUrl: String,
    // 请求方式
    val httpMethod: HttpMethod
)