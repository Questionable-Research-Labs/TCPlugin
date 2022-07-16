package nz.laspruca.tcplugin.eventListeners

import org.bukkit.CropState
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockGrowEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.qrl.tcplugin.TCPlugin.Companion.config
import org.qrl.tcplugin.TCPlugin.Companion.logger

private var crops: List<Material> = mutableListOf()

fun loadCrops() {
    crops = config.getStringList("crops").mapNotNull { mat ->
        try {
            Material.valueOf(mat)
        } catch (_: IllegalArgumentException) {
            logger.warning("No material for $mat")
            null
        }
    }.toList()
}

object CropHarvestEvent : Listener {
    @EventHandler
    fun cropInteractEvent(e: PlayerInteractEvent) {
        with(e) {
            clickedBlock?.let { block ->
                val drops = block.drops
                drops.lastOrNull()?.let { lastDrop ->
                    if (lastDrop.type in crops && lastDrop.amount > 1) {
                        val inv = player.inventory
                        for (drop in drops) {
                            var dropAmount = drop.amount
                            for (slot in inv.contents) {
                                if (slot != null && slot.type == drop.type) {
                                    logger.info("Gaming")
                                    val slotAmount = slot.amount
                                    if (slotAmount + dropAmount > 64) {
                                        logger.info("Setting drop amount to 64 with remainder")
                                        slot.amount = 64
                                        dropAmount = slotAmount + dropAmount - 64
                                    } else {
                                        logger.info("Setting drop amount to " + (slotAmount + dropAmount))
                                        slot.amount = slotAmount + dropAmount
                                        dropAmount = 0
                                    }
                                    logger.info("Done cycle, drop amount is $dropAmount")
                                }
                            }
                            logger.info("Drop amount for " + drop.type + " is " + dropAmount)
                            if (dropAmount > 0) {
                                val nextFree = inv.firstEmpty()
                                drop.amount = dropAmount
                                if (nextFree < 0) {
                                    player.world.dropItem(player.location, drop)
                                } else {
                                    inv.setItem(nextFree, drop)
                                }
                            }
                            logger.info("Droped items")
                        }

                        val data = player.server.createBlockData(block.type)
                        block.blockData = data

                        e.isCancelled = true
                    }
                }
            }
        }
    }
}