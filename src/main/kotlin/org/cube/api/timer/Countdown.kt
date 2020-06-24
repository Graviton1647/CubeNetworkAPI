package org.cube.api.timer

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

abstract class Countdown(private val plugin : Plugin, var time : Long) {

    abstract fun onFinish()

    abstract fun onTick()

    lateinit var timer : BukkitTask

    var timeLeft = time

    fun start() {
        timer = object : BukkitRunnable() {
            override fun run() {
                if(timeLeft <= 0) {
                    cancel()
                    onFinish()
                } else {
                    onTick()
                    timeLeft--
                }

            }
        }.runTaskTimer(plugin, 0,20L)
    }
}
