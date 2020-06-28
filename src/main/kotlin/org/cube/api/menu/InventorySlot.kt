package org.cube.api.menu

import org.bukkit.inventory.ItemStack

class InventorySlot(
    val name : String,
    val material : ItemStack,
    val lore : List<String>,
    val slot : Int,
    vararg val allow : ActionType
)