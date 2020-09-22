package de.fruxz.report.domain

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.serialization.ConfigurationSerializable

/**
 * This class helps to structure, design and
 * send messages
 * @author Fruxz
 * @since v1.0
 */
class Transmission : ConfigurationSerializable {

    /**
     * Generates a default transmission (with default message)
     * @param message message-content
     * @author Fruxz
     * @since v1.0
     */
    constructor(message: String) {
        transmissionContent += message
    }

    /**
     * Generates a transmission out of a deserialisation
     * @author Fruxz
     * @since v1.0
     */
    constructor(map: Map<String, Any>) {
        transmissionContent = map["message"] as String
    }

    /**
     * Transmission-Content (generates Prefix)
     * @author Fruxz
     * @since v1.0
     */
    var transmissionContent = "§7» §cPS-Report §8| "

    /**
     * Sends the message to the target [commandSender]
     * @param commandSender defines the target
     * @author Fruxz
     * @since v1.0
     */
    fun send(commandSender: CommandSender) {
        commandSender.sendMessage(transmissionContent)
    }

    /**
     * Broadcasts the message to everyone
     * @author Fruxz
     * @since v1.0
     */
    fun broadcast() {
        Bukkit.broadcastMessage(transmissionContent)
    }

    override fun serialize(): Map<String, Any> = mapOf(Pair("message", transmissionContent))

}