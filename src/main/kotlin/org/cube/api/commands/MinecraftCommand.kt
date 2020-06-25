package org.cube.api.commands


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)

annotation class MinecraftCommand(
    val name: String,
    val permission: String = "",
    val noPerm: String = "You do not have permission to perform that action",
    val aliases: Array<String> = [],
    val description: String = "",
    val usage: String = "",
    val inGameOnly: Boolean = false

)