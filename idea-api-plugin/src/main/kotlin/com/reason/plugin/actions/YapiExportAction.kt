package com.reason.plugin.actions

import com.reason.plugin.export.DataExporter
import com.reason.plugin.export.YapiDataExporter
import com.reason.plugin.infra.ApiContextUtil
import com.reason.plugin.infra.PsiContainer
import com.reason.plugin.resovler.ExportDataResolver
import com.reason.plugin.resovler.SpringExportDataResolver

/**
 * @author impassive
 */
open class YapiExportAction : AbstractAction("Export To Yapi") {

    private lateinit var exportDataResolver: ExportDataResolver

    private lateinit var dataExporter: DataExporter
    override fun doExport(psiContainer: PsiContainer): Boolean {
        // 根据 注解，选择 不同的解析器
        //1. 解析 为 指定的对象
        val exportData = exportDataResolver.resolvePsiClassData(psiContainer)
        //2. 导出 数据
        // dataExporter.export(exportData)
        return false
    }

    override fun doBefore() {
        dataExporter = ApiContextUtil.get().loadService(YapiDataExporter::class.java)
        exportDataResolver = ApiContextUtil.get().loadService(SpringExportDataResolver::class.java)
    }


}