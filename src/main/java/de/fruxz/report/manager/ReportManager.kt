package de.fruxz.report.manager

import de.fruxz.report.domain.FileManager
import de.fruxz.report.domain.Report
import org.bukkit.configuration.MemorySection
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * This class helps to easily manage current
 * reports, search-filters and more things.
 * @author Fruxz
 * @since v1.0
 */
class ReportManager {

    /**
     * This ***val*** contains the file of the
     * reports and its save/read-structure
     * @author Fruxz
     * @since v1.0
     */
    val data = FileManager("reports.data")

    /**
     * This ***var*** helps to save and read the current
     * and new reports and its changes
     * @author Fruxz
     * @since v1.0
     */
    @Suppress("UNCHECKED_CAST")
    var reports: SortedMap<Int, Report>
        get() {
            val keys = data.get<MemorySection>("reports")?.getKeys(false) ?: emptySet()
            val out = HashMap<Int, Report>()

            keys.forEach {
                val o = data.get<Report>("reports.$it")

                if (o != null)
                    out[it.toInt()] = o
            }

            //return data.get<HashMap<Int, Report>>("reports")?.toSortedMap() ?: emptyMap<Int, Report>().toSortedMap()

            return if (!out.isNullOrEmpty()) {
                out.toSortedMap()
            } else
                emptyMap<Int, Report>().toSortedMap()
        }
        set(value) {
            data.set("reports", value)
        }

    fun hasReported(reporter: UUID) = reports.values.any { it.reporter == reporter }
    fun isReported(reported: UUID) = reports.values.any { it.reported == reported }

    fun isReportedAmount(reported: UUID) = reports.values.filter { it.reported == reported }.size
    fun hasReportedAmount(reporter: UUID) = reports.values.filter { it.reporter == reporter }.size

    /**
     * This function returns every single report
     * sorted by the report-amounts in a ArrayList
     * @return sorted report list
     * @author Fruxz
     * @since v1.0
     */
    fun allReports(): ArrayList<Pair<Int, Report>> {
        val allReports = ArrayList<Pair<Int, Report>>()

        reports.forEach { (first, second) ->
            allReports.add(Pair(first, second))
        }

        allReports.sortBy { isReportedAmount(it.second.reported) }

        return allReports
    }

}