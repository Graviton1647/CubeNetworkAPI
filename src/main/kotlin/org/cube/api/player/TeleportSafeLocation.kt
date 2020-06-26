package org.cube.api.player

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs
import kotlin.math.max


object TeleportSafeLocation {

    fun getRandomLocation(radius: Int, centre: Location): Location {
        val maxX = centre.blockX + radius
        val minX = centre.blockX - radius
        val maxZ = centre.blockZ + radius
        val minZ = centre.blockZ - radius
        val ix: Int = ThreadLocalRandom.current().nextInt(max(abs(maxX - minX), 1)) + minX
        val x = ix + 0.5
        val iz: Int = ThreadLocalRandom.current().nextInt(max(abs(maxZ - minZ), 1)) + minZ
        val z = iz + 0.5
        return Location(centre.world, x, centre.world!!.getHighestBlockYAt(ix, iz).toDouble(), z)
    }

    fun isSafe(loc: Location, notSafe : Array<Material>): Boolean {
        val world = loc.world
        val x = loc.blockX
        val y = loc.blockY
        val z = loc.blockZ

        val block = loc.world!!.getBlockAt(x, y, z)
        val below = loc.world!!.getBlockAt(x, y - 1, z)
        val above = loc.world!!.getBlockAt(x, y + 1, z)

        return !(notSafe.contains(below.type) && block.type.isSolid && notSafe.contains(above.type) && block.type.isOccluding)
    }


}


class FindSafeLocationRunnable(private val player: Player,private val centre: Location, private val radius: Int, private val maxChecks: Int, private val message : String, private val notSafe : Array<Material>) : BukkitRunnable() {
    var checksLeft = maxChecks

    override fun run() {
        if (checksLeft-- <= 0) {
            player.sendMessage(message)
            cancel()
        }

        val testLocation: Location = TeleportSafeLocation.getRandomLocation(radius, centre)
        if (TeleportSafeLocation.isSafe(testLocation,notSafe)) {
            player.teleport(testLocation)
            cancel()
        }
    }
}


