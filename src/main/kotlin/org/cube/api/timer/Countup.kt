package org.cube.api.timer

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

abstract class Countup(private val plugin : Plugin, val time : Long) {

    abstract fun onFinish()

    abstract fun onTick()

    var currenttime = 0

    fun start() {

        object : BukkitRunnable() {
            override fun run() {
                if(time >= time) {
                    cancel()
                    onFinish()
                } else {
                    onTick()
                    currenttime++
                }

            }
        }.runTaskTimer(plugin, 0,20L)
    }
}