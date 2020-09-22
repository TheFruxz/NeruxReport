package de.fruxz.report.manager

import de.fruxz.report.domain.Report
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class ReportManager {

    private val configFile = File("plugins/NeruxReport", "reports.data")
    private val loader = YamlConfiguration.loadConfiguration(configFile)

    @Suppress("UNCHECKED_CAST")
    var reports: LinkedHashMap<Int, Report>
        get() {
            loader.load(configFile)
            return loader.get("reports") as LinkedHashMap<Int, Report>
        }
        set(value) {
            loader.load(configFile)
            loader.set("reports", value)
            loader.save(configFile)
        }

    fun hasReported(reporter: UUID) = reports.values.any { it.reporter == reporter }
    fun isReported(reported: UUID) = reports.values.any { it.reported == reported }

    fun isReportedAmount(reported: UUID) = reports.values.filter { it.reported == reported }.size
    fun hasReportedAmount(reporter: UUID) = reports.values.filter { it.reporter == reporter }.size

    fun allReports(): ArrayList<Pair<Int, Report>> {
        val allReports = ArrayList<Pair<Int, Report>>()

        reports.forEach { (first, second) ->
            allReports.add(Pair(first, second))
        }

        allReports.sortBy { isReportedAmount(it.second.reported) }

        return allReports
    }

}