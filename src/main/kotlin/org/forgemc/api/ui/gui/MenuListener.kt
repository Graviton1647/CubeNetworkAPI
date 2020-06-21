package org.forgemc.api.ui.gui

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.InventoryHolder

class MenuListener : Listener {

    @EventHandler
    fun onMenuClick(event : InventoryClickEvent) {
        val holder : InventoryHolder = event.inventory.holder!!
        if (holder is Menu) {
            event.isCancelled = true
            if(event.currentItem != null) {
                val menu : Menu = holder
                menu.handleMenu(event)
            }
        }
    }

    @EventHandler
    fun onMenuClose(event : InventoryCloseEvent) {
        val holder : InventoryHolder = event.inventory.holder!!
        if (holder is Menu) {
            val menu : Menu = holder
            menu.handleClose(event)
        }
    }

    @EventHandler
    fun onMenuOpen(event : InventoryOpenEvent) {
        val holder : InventoryHolder = event.inventory?.holder ?: return
        if (holder is Menu) {
            val menu : Menu = holder
            menu.handleOpen(event)
        }
    }

}