package org.forgemc.api.actionbar

import com.google.common.base.Preconditions
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.forgemc.api.MinecraftPlugin
import org.json.simple.JSONObject

class ActionBar(val text: String) {

    private var json: JSONObject

    /**
     * Constructs an [ActionBar] object based on plain text.
     */
    init {
        Preconditions.checkNotNull(text)
        json = convert(text)
    }

    /**
     * Sends an action bar message to a specific player.
     *
     * @param player The player to send the message to.
     */
    fun send(player: Player) {
        Preconditions.checkNotNull(player)
        try {
            val clsIChatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent")
            val clsChatMessageType = ServerPackage.MINECRAFT.getClass("ChatMessageType")
            val entityPlayer = player.javaClass.getMethod("getHandle").invoke(player)
            val playerConnection = entityPlayer.javaClass.getField("playerConnection")[entityPlayer]
            val chatBaseComponent =
                ServerPackage.MINECRAFT.getClass("IChatBaseComponent\$ChatSerializer").getMethod(
                    "a",
                    String::class.java
                ).invoke(null, json.toString())
            val chatMessageType =
                clsChatMessageType.getMethod("valueOf", String::class.java).invoke(null, "GAME_INFO")
            val packetPlayOutChat = ServerPackage.MINECRAFT.getClass("PacketPlayOutChat")
                .getConstructor(clsIChatBaseComponent, clsChatMessageType)
                .newInstance(chatBaseComponent, chatMessageType)
            playerConnection.javaClass.getMethod("sendPacket", ServerPackage.MINECRAFT.getClass("Packet"))
                .invoke(playerConnection, packetPlayOutChat)
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
    }

    fun convert(text: String): JSONObject {
        val json = JSONObject()
        for (i in 0..99) {
        }
        json["text"] = text
        return json
    }


    /**
     * Sends an action bar message to all online players.
     */
    fun sendToAll() {
        for (player in Bukkit.getOnlinePlayers()) {
            send(player)
        }
    }

    /**
     * Changes the text to display.
     *
     * @param text Text to display.
     */
    fun setText(text: String) {
        Preconditions.checkNotNull(text)
        json = convert(text)
    }


}