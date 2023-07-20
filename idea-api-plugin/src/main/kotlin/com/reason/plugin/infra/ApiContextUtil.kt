package com.reason.plugin.infra

/**
 * @author impassive
 */
object ApiContextUtil {

    private val localVal = ThreadLocal<ApiContext>()

    fun get(): ApiContext {
        return localVal.get()
    }

    fun set(apiContext: ApiContext) {
        localVal.set(apiContext)
    }

    fun clean() {
        localVal.remove()
    }

}