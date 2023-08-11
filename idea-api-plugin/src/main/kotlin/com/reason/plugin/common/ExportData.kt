package com.reason.plugin.common

import com.reason.support.common.ExportItem

/**
 * @author impassive
 */
data class ExportData(
    val exportName: String,
    val items: List<ExportItem>
)