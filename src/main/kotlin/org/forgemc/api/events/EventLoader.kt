package org.forgemc.api.events

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
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

    fun load(plugin: JavaPlugin) {

        val config = ConfigurationBuilder()
            .addScanners(
                SubTypesScanner(false),
                TypeAnnotationsScanner(),
                MethodAnnotationsScanner()
            )
            .addUrls(MinecraftEvent::class.java.getResource(""))

        val reflection = Reflections(config)
        val cds = reflection.getTypesAnnotatedWith(MinecraftEvent::class.java)
        if(cds.size != 0) {
            plugin.logger.info { "Registered ${cds.size} Events." }
            cds.forEach {
                val annotation = it.getAnnotation(MinecraftEvent::class.java)

                if (!EVENTS.contains(annotation.name)) {
                    try {
                        val instance = it.getConstructor().newInstance() as Listener
                        plugin.server.pluginManager.registerEvents(instance, plugin)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}