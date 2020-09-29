package de.fruxz.report.command

import de.fruxz.report.domain.Transmission
import de.fruxz.report.manager.ReportMaxAmountManager
import de.fruxz.report.utils.NumberUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class MaxActiveReportAmountCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (command.permission != null && sender.hasPermission(command.permission)) {
            val currentValue = ReportMaxAmountManager().getMaxActiveReportAmount()

            if (args.size == 1) {

                if (NumberUtils().isInt(args[0])) {
                    val nextValue = args[0].toInt()
                    val additionalWord = if (nextValue > currentValue) {
                        "erhöht"
                    } else
                        "gesenkt"

                    ReportMaxAmountManager().setMaxActiveReportAmount(nextValue)

                    Transmission("§7Der aktuelle Wert, der maximalen aktiven Reports pro Spieler, wurde auf §a$nextValue $additionalWord§7!").send(sender)

                } else
                    Transmission("§7Der Eingabewert §c${args[0]} §7muss einer Zahl entsprechen!").send(sender)

            } else if (args.isEmpty()) {

                Transmission("§7Aktuell kann ein Spieler §amaximal $currentValue Reports§7 gleichzeitig aktiv haben.").send(sender)

            } else
                Transmission("§7Bitte benutze §c${command.usage.replace("<command>", label)}§7!").send(sender)

        } else
            sender.sendMessage(command.permissionMessage)

        return true
    }

}