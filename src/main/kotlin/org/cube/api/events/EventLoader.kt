package org.cube.api.events

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.cube.api.menu.MenuListener


object EventLoader {

    val EVENTS: MutableMap<String, Listener> = HashMap()

    /**
     *  Loads events that have the [MinecraftEvent] annotation
     */

    fun load(plugin: JavaPlugin,classes : MutableList<Listener>) {

        classes.add(MenuListener())

        var events = 0
        classes.forEach {

                if (it.javaClass.getAnnotation(MinecraftEvent::class.java) != null) {
                    plugin.server.pluginManager.registerEvents(it.javaClass.newInstance(), plugin)
                    events++
                }

        }
        if(events != 0) {
            plugin.logger.info { "Registered $events Events." }
        }
    }

}