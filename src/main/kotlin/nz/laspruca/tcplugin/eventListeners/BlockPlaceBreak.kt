package nz.laspruca.tcplugin.eventListeners

import nz.laspruca.tcplugin.logger.BreakBlockEvent
import nz.laspruca.tcplugin.logger.PlaceBlockEvent
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.logger
import java.lang.Exception
import java.util.ArrayList

private var breakBlocks: List<Material> = ArrayList()
private var placeBlocks: List<Material> = ArrayList()


fun loadBlocks() {
    breakBlocks = config.getStringList("blocks-break").mapNotNull {
        try {
            Material.valueOf(it.toUpperCase())
        } catch (_: java.lang.IllegalArgumentException) {
            logger.warning("No material for $it")
            null
        }
    }.toList()

    placeBlocks = config.getStringList("place-break").mapNotNull {
        try {
            Material.valueOf(it.toUpperCase())
        } catch (_: java.lang.IllegalArgumentException) {
            logger.warning("No material for $it")
            null
        }
    }.toList()
}

object BlockPlaceBreak : Listener {
    @EventHandler
    fun onPlayerBreakBlock(e: BlockBreakEvent) {
        val player: HumanEntity = e.player
        val block = e.block
        if (breakBlocks.contains(block.type)) {
            BreakBlockEvent(player.name, block).log()
        }
    }

    @EventHandler
    fun onPlayerPlaceBlock(e: BlockPlaceEvent) {
        val player: HumanEntity = e.player
        val block = e.block
        if (placeBlocks.contains(block.type)) {
            PlaceBlockEvent(player.name, block).log()
        }
    }
}