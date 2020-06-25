package org.cube.api.commands

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MinecraftCompleter(
    val name: String,
    val aliases: Array<String> = []
)