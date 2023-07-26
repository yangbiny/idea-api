package com.reason.plugin.common.utils

import com.intellij.lang.Language
import com.reason.plugin.resovler.items.KotlinClassResolver

/**
 * @author impassive
 */
object SPIUtils {
    fun <T> resolveClass(language: Language, classs: Class<T>): T {
        return when (language) {
            Language.findLanguageByID("kotlin") -> {
                KotlinClassResolver() as T
            }
            else -> {
                throw RuntimeException("not support language: ${language.displayName}")
            }
        }
    }

}