package com.reason.support.common

data class ExportItem(
    val baseRequestMapping: String,
    // 请求信息
    val requestInfo: MethodRequestInfo,
    // 请求参数
    val requestMethodParam: List<MethodParam>,
    // 请求返回值
    val requestReturnInfo: MethodReturnInfo,
)