package de.fruxz.report.command

import com.google.common.base.Joiner
import de.fruxz.report.NeruxReport
import de.fruxz.report.domain.FileManager
import de.fruxz.report.domain.Report
import de.fruxz.report.domain.Transmission
import de.fruxz.report.manager.ReportCooldownManager
import de.fruxz.report.manager.ReportManager
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import kotlin.collections.ArrayList
import kotlin.random.Random

class ReportPlayerCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (command.permission == null || sender.hasPermission(command.permission)) {

            if (sender is Player) {
                val cooldownManager = ReportCooldownManager(sender.uniqueId)

                if (!cooldownManager.isCooldowning()) {
                    if (args.size >= 2) {
                        if (ReportManager().hasReportedAmount(sender.uniqueId) <= 20) {
                            val target = Bukkit.getOfflinePlayer(args[0])

                            if (target != null && target.hasPlayedBefore()) {
                                if (ReportManager().reports.filter { it.value.reporter == sender.uniqueId && it.value.reported == target.uniqueId }.isEmpty()) {
                                    val arguments = ArrayList<String>()

                                    arguments.addAll(args)

                                    if (args.isNotEmpty())
                                        arguments.removeAt(0)

                                    val reason = Joiner.on(" ").skipNulls().join(arguments)
                                    val finalReport = Report.generateReport(sender, target, reason)
                                    val reportID = Random.nextInt(10000, 99999)
                                    val currentReports = ReportManager().reports

                                    currentReports[reportID] = finalReport
                                    ReportManager().reports = currentReports

                                    cooldownManager.set(60)

                                    val reportAmounts = ReportManager().isReportedAmount(target.uniqueId)

                                    Transmission("§7Der Report gegen §e${target.name}§7 wurde erfolgreich unter der ID §a$reportID §7erstellt!").send(sender)

                                    sender.playSound(sender.location, Sound.NOTE_PLING, 1F, 2F)
                                    sender.playSound(sender.location, Sound.CHEST_CLOSE, 1F, 2F)
                                    sender.playSound(sender.location, Sound.HORSE_ARMOR, 1F, 0F)

                                    Bukkit.getOnlinePlayers().filter { it.hasPermission(NeruxReport.permissionReportManager) }.forEach {
                                        Transmission("§7Der Spieler §c${target.name}§7 wurde von §e${sender.name}§7 gemeldet!, ID: §a$reportID§7! §e§l(Insgesamt $reportAmounts Meldungen)").send(it)
                                    }

                                } else
                                    Transmission("§7Den Spieler §e${args[0]} §7hast du §cbereits gemeldet§7!").send(sender)
                            } else
                                Transmission("§7Der Spieler §e${args[0]} §7 wurde §cnoch nie §7gesehen!").send(sender)
                        } else
                            Transmission("§7Du kannst nur §cmaximal 20 Personen gleichzeitig §7aktiv gemeldet haben!").send(sender)
                    } else
                        Transmission("§7Nutze §e/$label <Spieler> <Grund...>§7!").send(sender)
                } else
                    Transmission("§7Du musst noch §e${cooldownManager.get()} Sekunden§7 warten, bis du erneut einen Spieler auf diesem P-Server melden kannst!").send(sender)

            } else
                Transmission("§7Dieser Befehl ist nicht für die Konsole geeignet!").send(sender)

        } else
            sender.sendMessage(command.permissionMessage)

        return true
    }

    val tabCompleter: TabCompleter = TabCompleter { _, _, _, _ ->
        val players = ArrayList<String>()

        for (player in Bukkit.getOnlinePlayers()) {
            players.add(player.name)
        }

        return@TabCompleter players
    }

}