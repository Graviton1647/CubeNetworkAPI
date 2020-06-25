package org.cube.api.commands

import org.apache.commons.lang.Validate
import org.bukkit.command.*
import org.bukkit.plugin.Plugin



class BukkitCommand(label: String, private val executor: CommandExecutor, private val plugin: Plugin) : Command(label) {

    var completer: BukkitCompleter? = null

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        var success : Boolean
        if (!testPermission(sender)) {
            return true
        }
        success = try {
            executor.onCommand(sender, this, commandLabel, args)
        } catch (ex: Throwable) {
            throw CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + plugin.description.fullName, ex)
        }
        if (!success && usageMessage.isNotEmpty()) {
            for (line in usageMessage.replace("<command>", commandLabel).split("\n").toTypedArray()) {
                sender.sendMessage(line)
            }
        }
        return success
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): List<String> {
        Validate.notNull(sender, "Sender cannot be null")
        Validate.notNull(args, "Arguments cannot be null")
        Validate.notNull(alias, "Alias cannot be null")
        var completions: List<String>? = null
        try {
            if (completer != null) {
                completions = completer!!.onTabComplete(sender, this, alias, args)
            }
            if (completions == null && executor is TabCompleter) {
                completions = (executor as TabCompleter).onTabComplete(sender, this, alias, args)
            }
        } catch (ex: Throwable) {
            val message = StringBuilder()
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ')
            for (arg in args) {
                message.append(arg).append(' ')
            }
            message.deleteCharAt(message.length - 1).append("' in plugin ").append(plugin.description.fullName)
            throw CommandException(message.toString(), ex)
        }
        return completions ?: super.tabComplete(sender, alias, args)
    }

    init {
        usageMessage = ""
    }
}