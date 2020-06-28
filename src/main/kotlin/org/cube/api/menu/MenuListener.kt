package org.cube.api.menu

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.InventoryHolder
import org.cube.api.events.MinecraftEvent
import java.awt.SystemColor.menu


@MinecraftEvent
class MenuListener : Listener {

    @EventHandler
    fun onMenuClick(event: InventoryClickEvent) {
        val holder : InventoryHolder = event.inventory?.holder ?: return
        holder.let {
            if (holder is Menu) {
                event.isCancelled = true
                if (event.currentItem == null) {
                    return
                }
                holder.onItemClick(event)
            }
        }
    }

    @EventHandler
    fun onMenuOpen(event: InventoryOpenEvent) {
        val holder : InventoryHolder = event.inventory?.holder ?: return
        holder.let {
            if (holder is Menu) {
                val menu : Menu = holder
                menu.onMenuOpen(event)
            }
        }
    }

    @EventHandler
    fun onMenuClose(event: InventoryCloseEvent) {
        val holder : InventoryHolder = event.inventory?.holder ?: return
        holder.let {
            if (holder is Menu) {
                val menu : Menu = holder
                menu.onMenuClose(event)
            }
        }
    }


}