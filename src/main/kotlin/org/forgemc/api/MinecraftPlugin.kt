package org.forgemc.api

/**
 *  [MinecraftPlugin]
 *  @param name Name of the plugin
 *  @param version Version of the server
 *  @param description description of the Plugin
 *  @param author author of the plugin
 *  @param prefix prefix of the plugin
 *
 */
annotation class MinecraftPlugin(
    val name : String,
    val version: String,
    val description : String = "",
    val author : String = "",
    val prefix : String = ""
)