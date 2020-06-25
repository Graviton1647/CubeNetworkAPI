package org.cube.api

import mu.KLogger
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.cube.api.commands.CommandManager
import org.cube.api.events.EventLoader
import kotlin.system.measureTimeMillis

class BasicPlugin(val javaPlugin: JavaPlugin) {

    fun start(plugin : Plugin, logger : KLogger) {
        makeData(plugin)
        logger.info { "Starting ${plugin.name}" }
        val time =  measureTimeMillis {
            EventLoader.load(javaPlugin)
            val commands = CommandManager(javaPlugin)
            commands.loadCommands(javaPlugin)
            commands.loadTabComplete()
        }
        logger.info { "${plugin.name} Started up in [${time.toDouble() / 1000.0}] seconds." }
    }

    fun stop(plugin : Plugin, logger : KLogger) {
        Bukkit.getOnlinePlayers().forEach {
            it.closeInventory()
        }
        logger.info { "Stopped ${plugin.name}" }
    }

    private fun makeData(plugin: Plugin) {
        if(!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }
    }


}