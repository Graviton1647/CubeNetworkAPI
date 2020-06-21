package org.forgemc.api.ui.gui


import org.bukkit.inventory.ItemStack
import org.dizitart.no2.Document
import org.forgemc.api.database.*

data class InventorySlot(
        val name : String,
        val material : ItemStack,
        val lore : List<String>,
        val slot : Int
) : DatabaseSerialisable {
    companion object {

        fun fromDocument(doc: Document) =
            InventorySlot(
                doc.getString("name"),
                doc.get("material", Document::class.java).getItemStack(),
                doc.getList("lore"),
                doc.getInt("slot")
            )
    }

    override fun asDocument() =
            Document.createDocument("name", name)
                    .put("material", material.asDocument())!!
                    .put("lore", lore)!!
                    .put("slot", slot)!!
}



