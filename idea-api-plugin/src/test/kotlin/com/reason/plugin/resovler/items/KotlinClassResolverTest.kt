package com.reason.plugin.resovler.items

import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.LightPlatformCodeInsightTestCase
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.io.File
import java.nio.charset.Charset

/**
 * @author impassive
 */
open class KotlinClassResolverTest : LightPlatformCodeInsightTestCase() {

    private val resolver = KotlinClassResolver()

    fun testResolvePsiFile() {
        val file =
            File("/Volumes/workspace/acm-operator/server/src/main/kotlin/com/alchemy/operator/infra/web/audit/AuditApi.kt")
        val reader = file.reader(Charset.defaultCharset())
        val readText = reader.readText()
        val project = super.getProject()
        val psiFile = PsiFileFactory.getInstance(project)
            .createFileFromText("test.kt", KotlinLanguage.INSTANCE, readText)
        val doResolve = resolver.doResolve(psiFile)
        println(doResolve)
    }

}