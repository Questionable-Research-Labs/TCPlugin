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

private var breakBlocks: MutableList<Material> = ArrayList()
private var placeBlocks: MutableList<Material> = ArrayList()


fun loadBlocks() {
    val `break` = config.getStringList("blocks-break")
    for (a in `break`) {
        try {
            breakBlocks.add(Material.valueOf(a.toUpperCase()))
        } catch (e: Exception) {
            logger.warning("Invalid block in config $a")
        }
    }

    breakBlocks.add(Material.CHEST)
    val place = config.getStringList("place-break")
    for (a in place) {
        try {
            placeBlocks.add(Material.valueOf(a.toUpperCase()))
        } catch (e: Exception) {
            logger.warning("Invalid block in config $a")
        }
    }
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