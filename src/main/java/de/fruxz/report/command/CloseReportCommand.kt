package de.fruxz.report.command

import de.fruxz.report.domain.Transmission
import de.fruxz.report.manager.ReportManager
import de.fruxz.report.utils.NumberUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CloseReportCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (command.permission == null || sender.hasPermission(command.permission)) {

            if (args.size == 1) {

                if (NumberUtils().isInt(args[0])) {

                    val reports = ReportManager()
                    val id = args[0].toInt()

                    if (reports.reports.containsKey(id)) {

                        val currentReport = reports.reports[id]
                        val clone = reports.reports

                        clone.remove(id)

                        reports.reports = clone

                        Transmission("§7Die Meldung gegen §e${Bukkit.getOfflinePlayer(currentReport?.reported).name}§7 mit der ID §a$id §7wurde erfolgreich geschlossen!").send(sender)

                        if (currentReport != null) {
                            if (Bukkit.getOfflinePlayer(currentReport.reporter).isOnline) {
                                val reporter = Bukkit.getPlayer(currentReport.reporter)

                                Transmission("§7Dein P-Server Report gegen §e${Bukkit.getOfflinePlayer(currentReport.reported).name} §7wurde §abearbeitet und abgeschlossen§7!").send(reporter)

                            }

                        }
                    } else
                        Transmission("§7Ein P-Server Report mit der ID §e$id§7 konnte §cnicht gefunden§7 werden!").send(sender)

                } else
                    Transmission("§7Der Eingabeparameter §e${args[0]} §7muss einer §cZahl entsprechen§7!").send(sender)

            } else
                Transmission("§7Bitte nutze §c/$label <Report-ID>§7!").send(sender)

        } else
            sender.sendMessage(command.permissionMessage)

        return true
    }

    val tabCompleter: TabCompleter = TabCompleter { _, _, _, args ->
        val out = ArrayList<String>()

        if (args.size == 1) {
            ReportManager().reports.keys.forEach {
                out.add("$it")
            }
        } else
            out.add(" ")

        return@TabCompleter out
    }

}