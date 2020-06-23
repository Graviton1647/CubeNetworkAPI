package org.cube.api.ui.gui

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.cube.api.player.delayBy

abstract class Menu(var name : String, private val rows : Int) : InventoryHolder {

    private lateinit var inv : Inventory

    abstract fun onItemClick(event : InventoryClickEvent)

    abstract fun onItemDrag(event : InventoryDragEvent)

    abstract fun onMenuClose(event : InventoryCloseEvent)

    abstract fun onMenuOpen(event : InventoryOpenEvent)

    abstract var items : ArrayList<InventorySlot>

    abstract var filler : InventorySlot



    fun open(player : Player) {
        inv = Bukkit.createInventory(this,9 * rows, ChatColor.translateAlternateColorCodes('&',name))
        if(!items.isNullOrEmpty()) {
            items.forEach {
                inventory.setItem(it.slot, createGuiItem(it))
            }
        }
        player.openInventory(inventory)
    }

    override fun getInventory(): Inventory = inv

    private fun createGuiItem(data : InventorySlot): ItemStack {
        val item = data.material
        val meta = item.itemMeta
        meta.displayName = data.name
        if(data.lore.isNotEmpty()) {
            meta.lore = data.lore
            item.itemMeta = meta
        }
        return item
    }

    open fun setFiller() {
        repeat(9 * rows) {
            if (inventory.getItem(it) == null) {
                inventory.setItem(it, createGuiItem(filler))
            }
        }
    }

}