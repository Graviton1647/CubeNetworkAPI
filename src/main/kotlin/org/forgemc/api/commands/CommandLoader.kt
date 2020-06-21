package org.forgemc.api.commands

import mu.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandMap
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.plugin.java.JavaPlugin

import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder
import java.util.logging.Logger
import java.lang.reflect.Field
import java.util.logging.Level

@SuppressWarnings("unused")
object CommandLoader {

    val logger = KotlinLogging.logger { }

    val COMMANDS: MutableMap<String, CommandExecutor> = HashMap()

    /**
     *  Loads commands that have the [MinecraftCommand] annotation
     */
    fun load(plugin: JavaPlugin) {
        val config = ConfigurationBuilder()
            .addScanners(
                SubTypesScanner(false),
                TypeAnnotationsScanner(),
                MethodAnnotationsScanner()
            )
            .addUrls(MinecraftCommand::class.java.getResource(""))

        val reflection = Reflections(config)
        val cds = reflection.getTypesAnnotatedWith(MinecraftCommand::class.java)
        if(cds.size != 0) {
            plugin.logger.info { "Registered ${cds.size} Commands." }
            cds.forEach {
                val annotation = it.getAnnotation(MinecraftCommand::class.java)
                if (!COMMANDS.contains(annotation.name)) {
                    try {
                        val instance = it.getConstructor().newInstance() as CommandExecutor
                        register(
                            annotation.name,
                            Command(
                                annotation.name,
                                annotation.description,
                                annotation.usageMessage,
                                annotation.aliasses,
                                instance
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


    /**
     *  register commands that have the [MinecraftCommand] annotation
     */

    fun register(fallback: String, command: BukkitCommand) {
        try {
            val bukkitCommandMap: Field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
            bukkitCommandMap.isAccessible = true
            val commandMap = bukkitCommandMap.get(Bukkit.getServer()) as CommandMap
            commandMap.register(fallback, command)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}