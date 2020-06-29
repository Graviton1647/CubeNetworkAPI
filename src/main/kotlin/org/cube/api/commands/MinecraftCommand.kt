package org.cube.api.commands

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)

annotation class MinecraftCommand(
        val name: String,
        val permission: String = "",
        val noPerm: String = "You do not have permission to perform that action",
        val aliases: Array<String> = [],
        val description: String = "",
        val usage: String = "",
        val inGameOnly: Boolean = false,
        val types : Array<KClass<out Any>> = [],
        val wrongArguments : String = "You did not put in the right arguments"
)