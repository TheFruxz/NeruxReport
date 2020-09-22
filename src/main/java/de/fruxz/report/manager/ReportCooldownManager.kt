package de.fruxz.report.manager

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.HashMap

class ReportCooldownManager(val uuid: UUID) {

    companion object {
        val cooldowns = HashMap<UUID, Int>()

        val runner = object : BukkitRunnable() {
            override fun run() {

                Bukkit.getOnlinePlayers().forEach {
                    val m = ReportCooldownManager(it.uniqueId)
                    val g = m.get()

                    if (g >= 1)
                        m.set(g-1)

                }

            }
        }

    }

    fun get(): Int = cooldowns[uuid] ?: 0

    fun set(value: Int) { cooldowns[uuid] = value }

    fun isCooldowning(): Boolean = get() >= 1

}