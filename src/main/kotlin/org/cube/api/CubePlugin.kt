package org.cube.api

import org.bukkit.plugin.java.JavaPlugin


class CubePlugin : JavaPlugin() {

    companion object {

        lateinit var manager : BasicPlugin

    }

    override fun onEnable() {
        manager = BasicPlugin(this)
    }

    override fun onDisable() {

    }


}