package org.cube.api.commands

import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.entity.Player
import org.bukkit.help.GenericCommandHelpTopic
import org.bukkit.help.HelpTopic
import org.bukkit.help.HelpTopicComparator
import org.bukkit.help.IndexHelpTopic
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*


class CommandManager(private val plugin: Plugin) : CommandExecutor {

    private val commandMap: MutableMap<String, Map.Entry<Method, Any>> = HashMap()
    lateinit var map: CommandMap

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        return handleCommand(sender, cmd, label, args)
    }


    fun handleCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        for (i in args.size downTo 0) {
            val buffer = StringBuffer()
            buffer.append(label.toLowerCase())
            for (x in 0 until i) {
                buffer.append("." + args[x].toLowerCase())
            }
            val cmdLabel = buffer.toString()
            if (commandMap.containsKey(cmdLabel)) {
                val method = commandMap[cmdLabel]!!.key
                val methodObject = commandMap[cmdLabel]!!.value
                val command = method.getAnnotation(MinecraftCommand::class.java)
                if (command.permission != "" && !sender.hasPermission(command.permission)) {
                    sender.sendMessage(command.noPerm)
                    return true
                }
                if (command.inGameOnly && sender !is Player) {
                    sender.sendMessage("This command is only performable in game")
                    return true
                }
                try {
                    method.invoke(
                            methodObject, CommandData(
                            sender, cmd, label, args,
                            cmdLabel.split("\\.").toTypedArray().size - 1
                    )
                    )
                } catch (e : IllegalArgumentException) {
                    // e.printStackTrace();
                } catch (e : IllegalAccessException) {
                    // e.printStackTrace();
                } catch (e : InvocationTargetException) {
                    //e.printStackTrace();
                }

                return true
            }
        }
        defaultCommand(CommandData(sender, cmd, label, args, 0))
        return true
    }

    fun loadCommands(plugin: JavaPlugin,classes : MutableList<Any>) {
        var events = 0
        classes.forEach {
            for (m in it.javaClass.methods) {
                if (m.getAnnotation(MinecraftCommand::class.java) != null) {
                    val command = m.getAnnotation(MinecraftCommand::class.java)
                    if (m.parameterTypes.size > 1 || m.parameterTypes[0] != CommandData::class.java) {
                        println("Unable to register command ${m.name}. Unexpected method arguments")
                        continue
                    }
                    registerCommand(command, command.name, m, it)
                    for (alias in command.aliases) {
                        registerCommand(command, alias!!, m, it)
                    }
                } else if (m.getAnnotation(MinecraftCompleter::class.java) != null) {
                    val comp: MinecraftCompleter = m.getAnnotation(MinecraftCompleter::class.java)
                    if (m.parameterTypes.size > 1 || m.parameterTypes.isEmpty() || m.parameterTypes[0] != CommandData::class.java) {
                        println("Unable to register tab completer ${m.name}. Unexpected method arguments")
                        continue
                    }
                    if (m.returnType != MutableList::class.java) {
                        println("Unable to register tab completer ${m.name}. Unexpected return type")
                        continue
                    }
                    registerCompleter(comp.name, m, it)
                    for (alias in comp.aliases) {
                        registerCompleter(alias!!, m, it)
                    }
                    events++
                }
            }
        }
        if(events != 0) {
            plugin.logger.info { "Registered $events Commands." }
        }
    }


    fun registerHelp() {
        val help: MutableSet<HelpTopic> = TreeSet(HelpTopicComparator.helpTopicComparatorInstance())
        for (s in commandMap.keys) {
            if (!s.contains(".")) {
                val cmd = map.getCommand(s)
                val topic: HelpTopic = GenericCommandHelpTopic(cmd!!)
                help.add(topic)
            }
        }
        val topic = IndexHelpTopic(plugin.name, "All commands for " + plugin.name, null, help, "Below is a list of all " + plugin.name + " commands:")
        Bukkit.getServer().helpMap.addTopic(topic)
    }

    fun registerCommand(minecraftCommand: MinecraftCommand, label: String, method: Method, obj: Any) {
        commandMap[label.toLowerCase()] = AbstractMap.SimpleEntry(method, obj)
        commandMap[plugin.name + ':' + label.toLowerCase()] = AbstractMap.SimpleEntry(method, obj)
        val cmdLabel = label.split("\\.").toTypedArray()[0].toLowerCase()
        if (map.getCommand(cmdLabel) == null) {
            val cmd: Command = BukkitCommand(cmdLabel, this, plugin)
            map.register(plugin.name, cmd)
        }
        if (!minecraftCommand.description.equals("", ignoreCase = true) && cmdLabel == label) {
            map.getCommand(cmdLabel)!!.description = minecraftCommand.description
        }
        if (!minecraftCommand.usage.equals("", ignoreCase = true) && cmdLabel == label) {
            map.getCommand(cmdLabel)!!.usage = minecraftCommand.usage
        }
    }

    fun registerCompleter(label: String, m: Method, obj: Any?) {
        val cmdLabel = label.split("\\.").toTypedArray()[0].toLowerCase()
        if (map.getCommand(cmdLabel) == null) {
            val command: Command = BukkitCommand(cmdLabel, this, plugin)
            map.register(plugin.name, command)
        }
        if (map.getCommand(cmdLabel) is BukkitCommand) {
            val command = map.getCommand(cmdLabel) as BukkitCommand
            if (command.completer == null) {
                command.completer = BukkitCompleter()
            }
            command.completer!!.addCompleter(label, m, obj!!)
        } else if (map.getCommand(cmdLabel) is PluginCommand) {
            try {
                val command: Any = map.getCommand(cmdLabel) as PluginCommand
                val field: Field = command.javaClass.getDeclaredField("completer")
                field.isAccessible = true
                when {
                    field.get(command) == null -> {
                        val completer = BukkitCompleter()
                        completer.addCompleter(label, m, obj!!)
                        field.set(command, completer)
                    }
                    field.get(command) is BukkitCompleter -> {
                        val completer = field.get(command) as BukkitCompleter
                        completer.addCompleter(label, m, obj!!)
                    }
                    else -> {
                        println("Unable to register tab completer ${m.name}. A tab completer is already registered for that command!")
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun defaultCommand(data: CommandData) {
        data.sender.sendMessage(data.label + " is not handled! Oh noes!")
    }

    init {
        if (plugin.server.pluginManager is SimplePluginManager) {
            val manager = plugin.server.pluginManager as SimplePluginManager
            val field: Field = SimplePluginManager::class.java.getDeclaredField("commandMap")
            field.isAccessible = true
            map = field.get(manager) as CommandMap
        }
    }
}


