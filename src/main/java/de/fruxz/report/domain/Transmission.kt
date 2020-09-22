package de.fruxz.report.domain

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.serialization.ConfigurationSerializable

class Transmission : ConfigurationSerializable {

    constructor(message: String) {
        transmissionContent += message
    }

    constructor(map: Map<String, Any>) {
        transmissionContent = map["message"] as String
    }

    var transmissionContent = "§7» §cPS-Report §8| "

    fun send(commandSender: CommandSender) {
        commandSender.sendMessage(transmissionContent)
    }

    fun broadcast() {
        Bukkit.broadcastMessage(transmissionContent)
    }

    fun broadcast(permission: String) {
        Bukkit.broadcast(transmissionContent, permission)
    }

    override fun serialize(): Map<String, Any> = mapOf(Pair("message", transmissionContent))

}