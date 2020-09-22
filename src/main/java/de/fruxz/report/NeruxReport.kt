package de.fruxz.report

import de.fruxz.report.domain.Report
import de.fruxz.report.domain.Transmission
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class NeruxReport : JavaPlugin() {

    override fun onLoad() {
        ser(Report::class.java)
        ser(Transmission::class.java)
        super.onLoad()
    }

    override fun onEnable() {
        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }

    private fun ser(clazz: Class<out ConfigurationSerializable>) {
        ConfigurationSerialization.registerClass(clazz)
    }

}