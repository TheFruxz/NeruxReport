package de.fruxz.report.manager

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.HashMap

/**
 * This class helps to easily manage read
 * and write cooldowns for the cooldowns
 * @author Fruxz
 * @since v1.0
 */
class ReportCooldownManager(val uuid: UUID) {

    companion object {

        /**
         * This ***val*** contains the cooldowns of
         * every single player
         * @author Fruxz
         * @since v1.0
         */
        val cooldowns = HashMap<UUID, Int>()

        /**
         * This [BukkitRunnable] controls the countdowns
         * of every single player
         * @author Fruxz
         * @since v1.0
         */
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

    /**
     * returns the current cooldowns value
     * or if null 0
     * @author Fruxz
     * @since v1.0
     */
    fun get(): Int = cooldowns[uuid] ?: 0

    /**
     * Sets the current cooldown value
     * @author Fruxz
     * @since v1.0
     */
    fun set(value: Int) { cooldowns[uuid] = value }

    /**
     * Returns, if the cooldown for the player
     * is already running
     * @return cooldown is running?
     * @author Fruxz
     * @since v1.0
     */
    fun isCooldowning(): Boolean = get() >= 1

}