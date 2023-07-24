import com.intellij.codeInsight.AnnotationUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.testFramework.TestDataProvider
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.kotlin.idea.refactoring.safeDelete.removeOverrideModifier
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.uast.evaluation.toConstant
import org.junit.Test
import java.awt.event.ActionEvent

/**
 * @author impassive
 */
open class MyActionTest : BasePlatformTestCase() {

    @Test
    fun testName() {

        val content = """
            package com.acm.api.facade.http.file

            import com.acm.api.application.file.DownloadFileCmd
            import com.acm.api.application.file.DownloadFileHandler
            import com.acm.api.application.file.DownloadUrlVo
            import com.duitang.base.core.dto.ApiResp
            import org.springframework.web.bind.annotation.PostMapping
            import org.springframework.web.bind.annotation.RequestBody
            import org.springframework.web.bind.annotation.RequestMapping
            import org.springframework.web.bind.annotation.RestController

            /**
             * @author impassive
             */
            @RestController
            @RequestMapping("/acm/api/dw/file/")
            open class DownloadFileApi(
                private val downloadFileHandler: DownloadFileHandler
            ) {

                @PostMapping("version/")
                open fun downloadFile(
                    @RequestBody
                    downloadFileCmd: DownloadFileCmd
                ): ApiResp<DownloadUrlVo> {
                    val handle = downloadFileHandler.handle(downloadFileCmd)
                    return ApiResp.success(handle)
                }


            }
        """.trimIndent()
        myFixture.configureByText("TestFile.kt", content)
        val file = myFixture.file
        val findFile = psiManager.findFile(file.virtualFile)

        val fileAnnotationList = (findFile as KtFile).fileAnnotationList

        // 获取类的名称
        val name = (findFile as KtFile).classes[0].name
        println(file.text)
        // VirtualFileImpl("TestFile.kt",KotlinFileType.INSTANCE,content.toByteArray())

    }
}