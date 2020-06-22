package org.cube.api.player

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask


/**
 * Delay an action by a number of seconds
 * @param plugin plugin instance
 * @param delay numbers of seconds to wait
 */
fun delayBy(plugin : Plugin,delay : Long, task : () -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            task.invoke()
        }
    }.runTaskLater(plugin, 20L * delay)
}

/**
 * Delay an action by a number of seconds
 * @param plugin plugin instance
 * @param delay numbers of seconds to wait
 */
fun runOnce(plugin : Plugin,delay : Long, task : () -> Unit ) {
    TimeUnit.MINUTES.toSeconds(5)
    object : BukkitRunnable() {
        override fun run() {
            task.invoke()
            cancel()
        }
    }.runTaskLater(plugin, 20L * delay)
}

/**
 * Run a action
 * @param plugin plugin instance
 */
fun runTask(plugin : Plugin, task : () -> Unit ) {
    object : BukkitRunnable() {
        override fun run() {
            task.invoke()
        }
    }.runTask(plugin)
}

/**
 * Delay an action by a number of seconds adn wait by a number of seconds
 * @param plugin plugin instance
 * @param wait numbers of seconds to start
 * @param delay numbers of seconds to wait
 */

fun delayByWait(plugin : Plugin, wait : Long, delay : Long, task : () -> Unit ) {
    object : BukkitRunnable() {
        override fun run() {
            task.invoke()
        }
    }.runTaskTimer(plugin,20L * wait,20L * delay)
}
