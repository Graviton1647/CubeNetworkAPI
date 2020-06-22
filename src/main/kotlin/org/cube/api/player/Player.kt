package org.cube.api.player

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.cube.api.CubePlugin



fun Player.message(vararg message : String) {
    message.forEach {
        sendMessage(ChatColor.translateAlternateColorCodes('&',it))
    }
}

/**
 * Sends a title and a subtitle message to the player.
 * @param title Title text
 * @param subtitle Subtitle text
 * @param fadeIn time in ticks for titles to fade in. Defaults to 10.
 * @param stay time in ticks for titles to stay. Defaults to 70.
 * @param fadeOut time in ticks for titles to fade out. Defaults to 20.
 */
fun Player.sendTitle(message : List<String>, sub : String = "", fadeIn : Int = 10, stay : Int = 70, fadeOut : Int = 20) {
    message.forEach {
        sendTitle(ChatColor.translateAlternateColorCodes('&',it),sub,fadeIn,stay,fadeOut)
    }
}

fun Player.sendTitle(message : String, sub : String = "", fadeIn : Int = 10, stay : Int = 70, fadeOut : Int = 20) {
    sendTitle(ChatColor.translateAlternateColorCodes('&',message),sub,fadeIn,stay,fadeOut)
}

fun Player.inRange(otherlocation : Location, distance : Double) : Boolean {
    return location.distance(otherlocation) <= distance
}

fun Player.teleport(
    location : Location,
    radius : Int,
    checks : Int,
    message: String = ChatColor.RED.toString() + "Couldn't find a safe place to teleport. Try again later.",
    notSafe : Array<Material> = arrayOf(Material.LAVA)
) {
    val findSafePlaceWorker: BukkitRunnable = FindSafeLocationRunnable(
        player,
        location,
        radius,
        checks,
        message,
        notSafe
    )
    findSafePlaceWorker.runTaskTimer(CubePlugin.plugin, 1, 1)
}


fun Inventory.addItems(vararg items : ItemStack, player : Player) {
    items.forEach { item ->
        var spaces = 0
        spaces += this.contents.filter { it == null }.map { 64 }.sum()

        if(spaces > 0) {
            addItem(item)
        } else {
            var toAdd = item.amount
            contents.filterNotNull()
                .filter { it.type == item.type }
                .filter { it.amount < 64 }
                .forEach { left ->
                    val canAdd = (64 - left.amount).coerceAtMost(toAdd)
                    left.amount += canAdd
                    toAdd -= canAdd
                }

            if(toAdd > 0) {
                item.amount = toAdd
                player.world.dropItem(player.location, item)
            }
        }
    }
}



