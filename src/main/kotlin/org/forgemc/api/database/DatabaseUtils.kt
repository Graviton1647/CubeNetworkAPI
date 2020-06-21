package org.forgemc.api.database

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.dizitart.no2.Document

fun Document.getString(name: String) = get(name, String::class.java)
fun Document.getInt(name: String) = get(name, Integer::class.java).toInt()
fun Document.getBoolean(name: String) = get(name, Boolean::class.java) as Boolean
fun Document.getShort(name: String) = get(name, java.lang.Short::class.java).toShort()
fun Document.getDouble(name: String) = get(name, java.lang.Double::class.java).toDouble()
fun Document.getFloat(name: String) = get(name, java.lang.Float::class.java).toFloat()
fun <K> Document.getList(name: String) = get(name, List::class.java) as List<K>
fun Document(name: String, value: Any) = Document.createDocument(name, value)

fun ItemStack.asDocument(): Document {
    return Document.createDocument("type", type.toString())
        .put("amount", amount)!!
        .put("durability", durability)!!
}

fun Document.getItemStack(): ItemStack {
    val type = Material.valueOf(getString("type"))
    val amount = getInt("amount")
    val dur = getShort("durability")
    return ItemStack(type, amount, dur)
}

