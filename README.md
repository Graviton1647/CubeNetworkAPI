# CubeNetworkAPI
 
# Welcome to CubeNetwork API!

 [![](https://jitpack.io/v/Graviton1647/ForgeMC-API.svg)](https://jitpack.io/#Graviton1647/ForgeMC-API) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin) [![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://github.com/Graviton1647/ForgeMC-API/graphs/commit-activity)  [![GitHub Forks](https://img.shields.io/github/license/Graviton1647/ForgeMC-API)](http://github.com/Graviton1647/ForgeMC-APIs/releases/) 


CubeNetwork API is made to go with Bukkit and uses it for the backend this is for QOL and quicker plugin development .


# Setup

To setup follow the instructions below.


**Gradle**
Add this to your dependencies

    compile 'com.github.Graviton1647:CubeNetwork-API:1.0'

and in your  **repositories** add the following 

    maven { url "https://jitpack.io" }
    


## Loading a plugin

To load a plugin all you need to do is extend your main class with `ForgePlugin` in place of `JavaPlugin`.

and then use the functions 

    start()
    stop()

in replace of

    onEnable()
    onStart()

and then add your main class to the YMAL like you would any other plugins


## Commands

To load a command all you need to do is make a class that extends `CommandExecutor` and use the following annotation 

    @MinecraftCommand("name", "desc", "useage", [])

This will automatically register the command to the plugin

## Events

To load a command all you need to do is make a class that extends `Listener` and use the following annotation 

    @MinecraftEvent("User")

This will automatically register the event to the plugin

## Events

To load a command all you need to do is make a class that extends `Listener` and use the following annotation 

    @MinecraftEvent("User")

This will automatically register the event to the plugin

## Player

Attached to the player their is some custom methods 

    player.message("f","f","f")

This will act the same as send message but send multiple message and also this takes in the colors 


    player.sendTitle


This will send multiple Titles to the player  and has support for fading


    player.teleport


This will teleport your player with optional parameters for what blocks are safe and how many checks you want it to do before giving up


 

## GUI

The GUI System is based off this [tutorial](https://www.youtube.com/watch?v=xebH6M_7k18) 
here is a example class written in KT 

    class KillConfirmMenu(playermenu : PlayerMenu) : Menu("Kill Yourself?",1,playermenu) {

    override var filler: ItemStack = ItemStack(Material.BARRIER,1)

    override var items: ArrayList<InventorySlot> = arrayListOf(
        InventorySlot("${ChatColor.GREEN}Yes", ItemStack(Material.EMERALD,1), listOf("${ChatColor.AQUA}Would you like to add", "${ChatColor.AQUA}this player to your lock?"),3),
        InventorySlot("${ChatColor.DARK_RED}No", ItemStack(Material.BARRIER,1), listOf(""),5)
    )

    override fun handleMenu(event: InventoryClickEvent) {
        when(event.currentItem.type) {
            Material.EMERALD -> {
                event.whoClicked.closeInventory()
                event.whoClicked.health = 0.0
            }
            Material.BARRIER -> {
                event.whoClicked.sendMessage("Changed your mind? aww.")
                event.whoClicked.closeInventory()
            }
        }
    }

    override fun handleOpen(event: InventoryOpenEvent) {
        println("Open")
    }

    override fun handleClose(event: InventoryCloseEvent) {
        print("Close")
    }

to load the GUI you would call 

KillConfirmMenu(player.getMenu()).open()
