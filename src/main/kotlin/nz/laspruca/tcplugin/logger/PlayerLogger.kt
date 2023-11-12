package nz.laspruca.tcplugin.logger

import nz.laspruca.tcplugin.util.getDate
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.Inventory
import org.qrl.tcplugin.TCPlugin.Companion.plugin
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/***
 * Initializes the logger
 */
fun init() {
    File("${plugin.dataFolder}/logs").mkdir()
}

/**
 * Used to log append a message to a players log file
 */
private fun logEvent(playerName: String, msg: String) {
    val f = File("${plugin.dataFolder}/logs/$playerName.txt")
    try {
        f.createNewFile()

        FileOutputStream(f, true).bufferedWriter().use {
            it.write("\n$msg")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * Base class for LoggerEvents, implements all default behaviour for events
 */
abstract class LoggerEvent (private val player: String) {
    protected var result: String = ""

    init {
        result += "[${getDate()}] "
    }

    /**
     * Function used to log information to the console
     */
    fun log() {
        logEvent(player, result)
    }
}

/**
 * Used to log breaking of a block
 */
class BreakBlockEvent(player: String, block: Block) : LoggerEvent(player) {
    init {
        result += "Break ${block.type} @ (${block.location.blockX} ${block.location.blockY} ${block.location.blockZ})"
    }
}

/**
 * Used to log placing of a block
 */
class PlaceBlockEvent(player: String, block: Block) : LoggerEvent(player) {
    init {
        result += "Place ${block.type} @ (${block.location.blockX} ${block.location.blockY} ${block.location.blockZ})"
    }
}

/**
 * Used to log the changing of an inventory
 */
class InventoryInteractEvent(player: String, inv: Inventory) : LoggerEvent(player) {
    init {
        result += "Break ${inv.type} @ (${inv.location?.blockX} ${inv.location?.blockY} ${inv.location?.blockZ})\n"

    }

    /**
     * Add that some items were added to the inventory
     */
    fun add(amount: Int, type: Material) {
        result += "\t Added $amount ${type.name}\n"
    }

    /**
     * Add that some items were removed from the inventory
     */
    fun remove(amount: Int, type: Material) {
        result += "\t Removed $amount ${type.name}\n"
    }
}

/**
 * Used to log a player joining
 */
class JoinEvent(player: String) : LoggerEvent(player) {
    init {
        result += "Joined Game"
    }
}

/*
* Used to log a player dying
* */
class DeathEvent(player: String, cause: String?) : LoggerEvent(player) {
    init {
        result += "Died (L) from $cause"
    }
}

/**
 * Used to log a player leaving
 */
class LeaveEvent(player: String) : LoggerEvent(player) {
    init {
        result += "Left game"
    }
}
