package org.cube.api.ui.gui

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.InventoryHolder
import org.cube.api.events.MinecraftEvent

@MinecraftEvent("eventname")
class MenuListener : Listener {

    @EventHandler
    fun onMenuClick(event : InventoryClickEvent) {
        val holder : InventoryHolder = event.inventory.holder!!
        if (holder is Menu) {
            event.isCancelled = true
            if(event.currentItem != null) {
                val menu : Menu = holder
                if(menu.items.find { it.slot == event.slot }!!.allow.contains(ActionType.CLICKABLE) ||  menu.items.find { it.slot == event.slot }!!.allow.isEmpty()) {
                    menu.onItemClick(event)
                }
            }
        }
    }

    @EventHandler
    fun onDragEvent(event : InventoryDragEvent) {
        val holder : InventoryHolder = event.inventory.holder!!
        if (holder is Menu) {
            val menu : Menu = holder
        }
    }

    @EventHandler
    fun onMenuClose(event : InventoryCloseEvent) {
        val holder : InventoryHolder = event.inventory.holder!!
        if (holder is Menu) {
            val menu : Menu = holder
            menu.onMenuClose(event)
        }
    }

    @EventHandler
    fun onMenuOpen(event : InventoryOpenEvent) {
        val holder : InventoryHolder = event.inventory?.holder ?: return
        if (holder is Menu) {
            val menu : Menu = holder
            menu.onMenuOpen(event)
        }
    }

}