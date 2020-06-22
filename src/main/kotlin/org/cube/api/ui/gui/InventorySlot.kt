package org.cube.api.ui.gui


import org.bukkit.inventory.ItemStack
import org.cube.api.database.*
import org.dizitart.no2.Document

class InventorySlot(val name : String,
        val material : ItemStack,
        val lore : List<String>,
        val slot : Int,
        vararg val allow : ActionType
)



