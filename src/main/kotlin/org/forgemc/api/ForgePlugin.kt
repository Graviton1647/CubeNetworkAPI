package org.forgemc.api

import mu.KotlinLogging
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.forgemc.api.commands.CommandLoader
import org.forgemc.api.events.EventLoader
import org.forgemc.api.ui.gui.PlayerMenu
import java.io.File
import java.io.Writer
import kotlin.system.measureTimeMillis


abstract class ForgePlugin() : JavaPlugin() {

    companion object {

        lateinit var plugin : JavaPlugin

        val logger = KotlinLogging.logger {  }

        val playerMenu : HashMap<Player, PlayerMenu> = hashMapOf()

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
        playerMenu.forEach {
            it.value.owner.closeInventory()
        }
        playerMenu.clear()
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