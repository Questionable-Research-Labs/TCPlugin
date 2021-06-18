package nz.laspruca.tcplugin.eventListeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap

object InventoryInteract : Listener {
    private var chestInventories = HashMap<UUID, HashMap<Material, Int?>>()

    @EventHandler
    fun onInventoryOpen(e: InventoryOpenEvent) {
        val inventory = e.inventory
        val player = e.player
        chestInventories[player.uniqueId] = collapse(listOf(*inventory.contents))
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val inventory = e.inventory
        val player = e.player
        when (inventory.type) {
            InventoryType.CHEST, InventoryType.BARREL, InventoryType.SHULKER_BOX -> {
                val opened = chestInventories[e.player.uniqueId]!!
                val closed = collapse(listOf(*e.inventory.contents))
                val log =
                    nz.laspruca.tcplugin.logger.InventoryInteractEvent(player.name, inventory)
                for (mat in opened.keys) {
                    val amountOpened = opened[mat]
                    val amountClosed = closed[mat]
                    if (amountClosed == null) {
                        log.remove(amountOpened!!, mat)
                    } else {
                        if (amountClosed < amountOpened!!) {
                            log.remove(amountClosed - amountOpened, mat)
                        } else if (amountClosed > amountOpened) {
                            log.add(amountOpened - amountClosed, mat)
                        }
                        closed.remove(mat)
                    }
                }
                for (mat in closed.keys) {
                    log.add(closed[mat]!!, mat)
                }
                log.log()
            }
            else -> {
            }
        }
    }

    private fun collapse(original: List<ItemStack?>): HashMap<Material, Int?> {
        val result = HashMap<Material, Int?>()
        for (a in original) {
            if (a != null) {
                result[a.type] = (if (result[a.type] == null) 0 else result[a.type])!! + a.amount
            }
        }
        return result
    }
}