package de.fruxz.report.command

import de.fruxz.report.domain.Transmission
import de.fruxz.report.manager.ReportManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ListReportsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (command.permission == null || sender.hasPermission(command.permission)) {

            val reportManager = ReportManager()
            var round = 0
            val border = Transmission("§8§m---§6 Meistgemeldete Spieler §8§m---")
            val empty = Transmission(" ")

            border.send(sender)
            for (entry in reportManager.reports) {
                val v = entry.value

                if (round <= 4) {

                    empty.send(sender)
                    Transmission("§7ID: §a${entry.key}§8; §a${Bukkit.getOfflinePlayer(v.reporter).name}§7 >> §c${Bukkit.getOfflinePlayer(v.reported).name} §6§l(${reportManager.isReportedAmount(v.reported)}*)§8; §7Grund: §e${v.reason}§8").send(sender)

                    round++
                } else
                    break
            }
            empty.send(sender)
            border.send(sender)

        }

        return true
    }
}