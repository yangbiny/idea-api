package com.reason.plugin.common.utils

import com.intellij.lang.Language
import java.util.*

/**
 * @author impassive
 */
object SPIUtils {

    private val RESOLVER_MAP = mutableMapOf<String, MutableMap<Class<*>, Any>>()

    fun <T> resolveClass(language: Language, tClass: Class<T>): T {
        val languageName = language.displayName
        RESOLVER_MAP[languageName]?.let {
            it[tClass]?.let { classMap ->
                @Suppress("UNCHECKED_CAST")
                return classMap as T
            }
        }

        val classLoader = SPIUtils::class.java.classLoader
        val inputStream = classLoader.getResourceAsStream(tClass.name)
        if (inputStream != null) {
            val properties = Properties()
            properties.load(inputStream)
            val className = properties.getProperty(languageName)
            if (className.isNullOrBlank()) {
                throw IllegalArgumentException("Not found class: ${tClass.name}")
            }
            val clazz = classLoader.loadClass(className)
            val instance = clazz.getDeclaredConstructor().newInstance()
            RESOLVER_MAP.computeIfAbsent(languageName) { mutableMapOf() }[tClass] = instance
            @Suppress("UNCHECKED_CAST")
            return instance as T
        }
        throw RuntimeException("Not found class: ${tClass.name}")
    }

}