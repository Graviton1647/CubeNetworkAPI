package org.cube.api.actionbar

import com.google.common.base.Preconditions
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player


class ActionBar(val text: String) {


    fun send(player: Player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(text))
    }

    fun sendToAll() {
        for (player in Bukkit.getOnlinePlayers()) {
            send(player)
        }
    }

}