package org.cube.api.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class CommandData(val sender : CommandSender, val command : Command, var label : String, val args: Array<String>, subCommand : Int) {

    init {
        val modArgs = arrayOfNulls<String>(args.size - subCommand)
        for (i in 0 until args.size - subCommand) {
            modArgs[i] = args[i + subCommand]
        }
        val buffer = StringBuffer()
        buffer.append(label)
        for (x in 0 until subCommand) {
            buffer.append("." + args[x])
        }
        val cmdLabel = buffer.toString()
        this.label = cmdLabel
    }

    fun isPlayer(): Boolean {
        return sender is Player
    }

    fun getPlayer(): Player? {
        return if (sender is Player) {
            sender
        } else {
            null
        }
    }
    
}