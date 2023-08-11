package com.reason.plugin.resovler

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.reason.plugin.infra.PsiContainer

/**
 * @author impassive
 */
class SpringExportDataResolverTest : BasePlatformTestCase() {

    private val springExportDataResolver = SpringExportDataResolver()

    fun testResolvePsiClassData() {
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

/*        springExportDataResolver.resolvePsiClassData(
            PsiContainer(
                psiFile = file,
                dataContext = mockk(),
            )
        )*/
    }
}