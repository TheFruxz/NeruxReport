package de.fruxz.report.domain

import org.bukkit.OfflinePlayer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.util.*

class Report : ConfigurationSerializable {

    companion object {
        fun generateReport(reporter: OfflinePlayer, reported: OfflinePlayer, reason: String): Report {
            return Report(reporter, reported, Calendar.getInstance(Locale.GERMANY), reason)
        }
    }

    val reporter: UUID
    val reported: UUID
    val date: Calendar
    val reason: String

    constructor(reporter: OfflinePlayer, reported: OfflinePlayer, date: Calendar, reason: String) {
        this.reporter = reporter.uniqueId
        this.reported = reported.uniqueId
        this.date = date
        this.reason = reason
    }

    constructor(reporter: UUID, reported: UUID, date: Calendar, reason: String) {
        this.reporter = reporter
        this.reported = reported
        this.date = date
        this.reason = reason
    }

    constructor(map: Map<String, Any>) {
        reporter = UUID.fromString(map["reporter"] as String)
        reported = UUID.fromString(map["reported"] as String)

        val cal = Calendar.getInstance(Locale.GERMANY)
        cal.time = map["time"] as Date
        this.date = cal
        this.reason = map["reason"] as String
    }

    override fun serialize(): Map<String, Any> = mapOf(
            Pair("reporter", reporter.toString()),
            Pair("reported", reported.toString()),
            Pair("time", date.time),
            Pair("reason", reason)
    )
}