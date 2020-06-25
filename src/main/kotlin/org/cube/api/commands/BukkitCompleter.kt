package org.cube.api.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.lang.reflect.Method
import java.util.*


class BukkitCompleter : TabCompleter {

    private val completers: MutableMap<String, Map.Entry<Method, Any>> = HashMap<String, Map.Entry<Method, Any>>()

    fun addCompleter(label: String, method: Method, obj: Any) {
        completers[label] = AbstractMap.SimpleEntry(method, obj)
    }


    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String> {
        for (i in args.size downTo 0) {
            val buffer = StringBuffer()
            buffer.append(label.toLowerCase())
            for (x in 0 until i) {
                if (args[x] != "" && args[x] != " ") {
                    buffer.append("." + args[x].toLowerCase())
                }
            }
            val cmdLabel = buffer.toString()
            if (completers.containsKey(cmdLabel)) {
                val entry = completers[cmdLabel]
                    return entry!!.key.invoke(
                        entry!!.value,
                        CommandData(sender, command, label, args, cmdLabel.split("\\.").toTypedArray().size - 1)
                    ) as List<String>
            }
        }
        return emptyList()
    }

}