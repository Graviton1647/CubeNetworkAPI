package org.cube.api.player

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64.encodeBase64
import java.util.*
import java.util.UUID
import java.util.stream.Collectors


fun getPlayerHead(owner: String): ItemStack {
    val newVersion = Arrays.stream(Material.values())
            .map { it.name }
            .collect(Collectors.toList())
            .contains("PLAYER_HEAD")


    val type = Material.matchMaterial(
        if (newVersion) "PLAYER_HEAD" else "SKULL_ITEM"
    )
    val item = ItemStack(type!!, 1)
    if (!newVersion) {
        item.durability = 3 as Short
    }
    val meta = item.itemMeta as SkullMeta
    meta.owningPlayer = Bukkit.getPlayer(UUID.fromString(owner))
    item.itemMeta = meta
    return item
}
