package org.cube.api.events

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.cube.api.commands.CommandData
import org.cube.api.commands.MinecraftCommand
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder
import java.util.logging.Logger
import java.util.logging.Level


object EventLoader {

    val EVENTS: MutableMap<String, Listener> = HashMap()

    /**
     *  Loads events that have the [MinecraftEvent] annotation
     */

    fun load(plugin: JavaPlugin,classes : MutableList<Listener>) {
        classes.forEach {
            for (m in it.javaClass.constructors) {
                if (m.getAnnotation(MinecraftEvent::class.java) != null) {
                    if (it.javaClass.interfaces !is Listener) {
                        println("Unable to register Listener . Does not Extend Listener")
                        continue
                    }
                    plugin.server.pluginManager.registerEvents(it.javaClass.newInstance(), plugin)
                }
            }
            plugin.logger.info { "Registered ${classes.size} Events." }
        }
    }

}