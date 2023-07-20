package com.reason.plugin.infra

import com.google.inject.Injector

/**
 * @author impassive
 */
data class ApiContext(
    val injector: Injector,
    val psiContainer: PsiContainer
) {
    fun <T> loadService(serviceClass: Class<T>): T {
        return injector.getInstance(serviceClass)
    }

}