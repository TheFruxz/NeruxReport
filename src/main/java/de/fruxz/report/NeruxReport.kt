package de.fruxz.report

import de.fruxz.report.command.ListReportsCommand
import de.fruxz.report.command.ReportPlayerCommand
import de.fruxz.report.domain.Report
import de.fruxz.report.domain.Transmission
import de.fruxz.report.manager.ReportCooldownManager
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class NeruxReport : JavaPlugin() {

    companion object {
        val permissionReportManager = "neruxreport.managing"
    }

    override fun onLoad() {
        ser(Report::class.java)
        ser(Transmission::class.java)
        super.onLoad()
    }

    override fun onEnable() {

        getCommand("psreport").executor = ReportPlayerCommand()
        getCommand("psreport").tabCompleter = ReportPlayerCommand().tabCompleter

        getCommand("pslistreports").executor = ListReportsCommand()

        ReportCooldownManager.runner.runTaskTimerAsynchronously(this, 20, 20)

        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }

    private fun ser(clazz: Class<out ConfigurationSerializable>) {
        ConfigurationSerialization.registerClass(clazz)
    }

}