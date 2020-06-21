package org.forgemc.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import mu.KotlinLogging
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder
import java.io.File


class Yml {

    private lateinit var annotation : MutableSet<Class<*>>

    private val logger = KotlinLogging.logger {  }

    private fun makeFile() : Boolean {
        checkAnnotations()
        if(annotation.size == 0 && !containsYml()) {
            logger.info { "You do not have a YML file please use the @MinecraftPlugin annotation or make your own" }
            return false
        }
        if(annotation.size > 1) {
            logger.info { "You only need 1 @MinecraftPlugin annotation" }
            return false
        }
        if (annotation.size != 0 && !containsYml()) {
            logger.info { "No YML Found Making it" }
            return true
        }
        return false
    }

    fun checkYml() {
        if(makeFile()) {
            annotation.forEach {
                val annotation = it.getAnnotation(MinecraftPlugin::class.java)
                try {
                    if(it.getConstructor().newInstance() !is ForgePlugin) {
                        val information = PluginFormat(
                            annotation.name,
                            annotation.version,
                            it.name,
                            annotation.description,
                            annotation.author,
                            annotation.prefix
                        )
                        val om = ObjectMapper(YAMLFactory())
                        om.writeValue(File("plugin.yml"), information)
                    } else {
                        logger.info { "@MinecraftPlugin annotation needs to extends ForgePlugin" }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun containsYml() : Boolean {
        return Yml::class.java.getResource("/plugin.yml") != null
    }

    /**
     *  Checks for the [MinecraftPlugin] annotation
     */
    private fun checkAnnotations() {
        val config = ConfigurationBuilder()
            .addScanners(
                SubTypesScanner(false),
                TypeAnnotationsScanner(),
                MethodAnnotationsScanner()
            )
            .addUrls(MinecraftPlugin::class.java.getResource(""))

        val reflection = Reflections(config)
        annotation = reflection.getTypesAnnotatedWith(MinecraftPlugin::class.java)
    }

}

data class PluginFormat(
    val name : String,
    val version: String,
    val main : String,
    val description : String = "",
    val author : String = "",
    val prefix : String = ""
)
