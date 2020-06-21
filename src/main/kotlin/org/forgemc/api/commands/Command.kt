package org.forgemc.api.commands

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

class Command(
    name: String,
    description : String,
    usageMessage : String,
    aliasses : Array<String>,
    private val executor : CommandExecutor
) : BukkitCommand(name) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        return executor.onCommand(sender, this, commandLabel, args)
    }

}