package org.cube.api.menu

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.*


abstract class Menu(var name : String, private val rows : Int) : InventoryHolder {

    lateinit var inv: Inventory

    abstract var filler : Material

    abstract fun onItemClick(event: InventoryClickEvent)

    abstract fun onMenuOpen(event : InventoryOpenEvent)

    abstract fun onMenuClose(event : InventoryCloseEvent)

    abstract var items : ArrayList<InventorySlot>

    fun open(player : Player) {
        inv = Bukkit.createInventory(this, 9 * rows, ChatColor.translateAlternateColorCodes('&',name))
        player.openInventory(inventory)
        if(!items.isNullOrEmpty()) {
            items.forEach {
                inventory.setItem(it.slot, createGuiItem(it))
            }
        }
    }

    override fun getInventory(): Inventory {
        return inv
    }

    fun setFillerGlass() {
        repeat(9 * rows) {
            if (inventory.getItem(it) == null) {
                inventory.setItem(it, ItemStack(filler,1))
            }
        }

    }

    private fun createGuiItem(data : InventorySlot): ItemStack {
        val item = data.material
        val meta = item.itemMeta
        meta!!.setDisplayName(data.name)
        if(data.lore.isNotEmpty()) {
            meta.lore = data.lore
        }
        item.itemMeta = meta
        return item
    }

}
