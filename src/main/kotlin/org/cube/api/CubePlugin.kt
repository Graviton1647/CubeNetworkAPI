package org.cube.api

import mu.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.cube.api.commands.CommandLoader
import org.cube.api.events.EventLoader
import kotlin.system.measureTimeMillis


abstract class CubePlugin : JavaPlugin() {

    companion object {

        lateinit var plugin : JavaPlugin

        val logger = KotlinLogging.logger {  }

    }

    override fun onEnable() {
       plugin = this
        makeData()
        logger.info { "Starting ${this.name}" }
        val time =  measureTimeMillis {
            EventLoader.load(this)
            CommandLoader.load(this)
            start()
        }
        logger.info { "${this.name} Started up in [${time.toDouble() / 1000.0}] seconds." }
    }

    override fun onDisable() {
        stop()
        Bukkit.getOnlinePlayers().forEach {
           it.closeInventory()
        }
        logger.info { "Stopped ${this.name}" }
    }

    /**
     * Make the data folder if it does not exists
     */
    private fun makeData() {
        if(!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
    }



    /**
     * Called when the plugin is enabled
     */
    abstract fun start()

    /**
     * Called when the plugin is disabled
     */
    abstract fun stop()

}