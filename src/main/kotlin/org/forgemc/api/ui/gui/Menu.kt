package org.forgemc.api.ui.gui

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

abstract class Menu(var name : String, private val rows : Int, private val playerMenu : PlayerMenu) : InventoryHolder {

    private lateinit var inv : Inventory

    abstract fun handleMenu(event : InventoryClickEvent)

    abstract var items : ArrayList<InventorySlot>

    abstract var filler : ItemStack

    fun open() {
        inv = Bukkit.createInventory(this,9 * rows, ChatColor.translateAlternateColorCodes('&',name))
        if(!items.isNullOrEmpty()) {
            items.forEach {
                inventory.setItem(it.slot, createGuiItem(it))
            }
        }
        playerMenu.owner.openInventory(inventory)
    }

    override fun getInventory(): Inventory = inv

    abstract fun handleClose(event : InventoryCloseEvent)

    abstract fun handleOpen(event : InventoryOpenEvent)

    fun createGuiItem(data : InventorySlot): ItemStack {
        val item = data.material
        val meta = item.itemMeta
        meta!!.setDisplayName(data.name)
        if(data.lore.isNotEmpty()) {
            meta!!.lore = data.lore
            item.itemMeta = meta
        }
        return item
    }

    open fun setFillerGlass() {
        repeat(9 * rows) {
            if (inventory.getItem(it) == null) {
                inventory.setItem(it, filler)
            }
        }
    }


}