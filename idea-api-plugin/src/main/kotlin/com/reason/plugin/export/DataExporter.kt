package com.reason.plugin.export

import com.reason.plugin.common.ExportData

/**
 * @author impassive
 */
interface DataExporter {

    fun export(exporter: ExportData): Boolean
}