package org.forgemc.api.commands

/**
 *  [MinecraftCommand]
 *  @param name Name of the command
 *  @param description Description of the command
 *  @param usageMessage Usage Message of the command
 *  @param aliasses Aliasses of the command
 */
annotation class MinecraftCommand(
    val name : String,
    val description : String,
    val usageMessage : String,
    val aliasses : Array<String>
)