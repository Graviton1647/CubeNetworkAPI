package org.cube.api.actionbar

import org.bukkit.Bukkit

internal enum class ServerPackage(private val path: String) {
    MINECRAFT("net.minecraft.server.${Bukkit.getServer().javaClass.getPackage().name.substring(23)}"),
    CRAFTBUKKIT("org.bukkit.craftbukkit.${Bukkit.getServer().javaClass.getPackage().name.substring(23)}");

    override fun toString(): String {
        return path
    }

    @Throws(ClassNotFoundException::class)
    fun getClass(className: String): Class<*> {
        return Class.forName("$this.$className")
    }


}