package org.cube.api.utils

import net.md_5.bungee.api.ChatColor

data class Color(val colorHex: String) {

    enum class TAGS(val tagValue: String) {
        COLOR("col"),
        STRIKETHROUGH("strike"),
        ITALIC("italic"),
        UNDERLINE("underline"),
        MAGIC("magic"),
        BOLD("bold");


        operator fun invoke(color: Color, value: Any?, text : Any?): String = "${getType(color,value)}$text"

        fun getType(color : Color, type : Any?) : ChatColor {
            when(type) {
                "col" -> return ChatColor.of(color.toString())
                "strike" -> return ChatColor.STRIKETHROUGH
                "italic" -> return ChatColor.ITALIC
                "underline" -> return ChatColor.UNDERLINE
                "magic" -> return ChatColor.MAGIC
                "bold" -> return ChatColor.BOLD
            }
            return ChatColor.DARK_GRAY
        }

    }

    override fun toString(): String = colorHex

}


infix fun Any?.colored(hex: Color): String = Color.TAGS.COLOR(hex, "col",this)
infix fun Any?.colored(hex: String): String = Color.TAGS.COLOR(Color(hex), "col",this)
infix fun Any.strike(hex: String): String = Color.TAGS.STRIKETHROUGH( Color(hex),"strike",this)
infix fun Any.italic(hex: String): String = Color.TAGS.ITALIC( Color(hex),"italic",this)
infix fun Any.underline(hex: String): String = Color.TAGS.UNDERLINE( Color(hex),"underline",this)
infix fun Any.magic(hex: String): String = Color.TAGS.MAGIC( Color(hex),"magic",this)
infix fun Any.bold(hex: String): String = Color.TAGS.BOLD( Color(hex),"bold",this)