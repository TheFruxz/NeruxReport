package de.fruxz.report

import de.fruxz.report.command.CloseReportCommand
import de.fruxz.report.command.ListReportsCommand
import de.fruxz.report.command.ReportPlayerCommand
import de.fruxz.report.domain.Report
import de.fruxz.report.domain.Transmission
import de.fruxz.report.manager.ReportCooldownManager
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

/**
 * This is the main class, it helps us to load systems,
 * start services and register classes
 * @author Fruxz
 * @since v1.0
 * @see JavaPlugin
 */
class NeruxReport : JavaPlugin() {

    companion object {

        /**
         * This is the permission, which is required to manage reports
         * of the NeruxReport P-Server Plugin
         * @author Fruxz
         * @since v1.0
         */
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

        getCommand("psreportslist").executor = ListReportsCommand()

        getCommand("psreportsclose").executor = CloseReportCommand()
        getCommand("psreportsclose").tabCompleter = CloseReportCommand().tabCompleter

        ReportCooldownManager.runner.runTaskTimerAsynchronously(this, 20, 20)

        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }

    /**
     * This function helps to easily register a serializable
     * class to the NeruxReport P-Server plugin
     * @author Fruxz
     * @since v1.0
     */
    private fun ser(clazz: Class<out ConfigurationSerializable>) {
        ConfigurationSerialization.registerClass(clazz)
    }

}