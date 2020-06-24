package org.cube.api

import mu.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.cube.api.commands.CommandLoader
import org.cube.api.events.EventLoader
import kotlin.system.measureTimeMillis


object CubePlugin {

    private val logger = KotlinLogging.logger {  }

    fun start(plugin : JavaPlugin, task : () -> Unit ) {
        makeData(plugin)
        logger.info { "Starting ${plugin.name}" }
        val time =  measureTimeMillis {
            EventLoader.load(plugin)
            CommandLoader.load(plugin)
            task.invoke()
        }
        logger.info { "${plugin.name} Started up in [${time.toDouble() / 1000.0}] seconds." }
    }

    fun disable(plugin : JavaPlugin, task : () -> Unit ) {
        Bukkit.getOnlinePlayers().forEach {
           it.closeInventory()
        }
        task.invoke()
        logger.info { "Stopped ${plugin.name}" }
    }

    /**
     * Make the data folder if it does not exists
     */
    private fun makeData(plugin : JavaPlugin) {
        if(!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }
    }

}