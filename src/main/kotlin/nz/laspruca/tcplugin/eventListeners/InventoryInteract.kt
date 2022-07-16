package nz.laspruca.tcplugin.eventListeners

import nz.laspruca.tcplugin.logger.InventoryInteractEvent
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
    private var chestInventories = HashMap<UUID, EnumMap<Material, Int>>()

    @EventHandler
    fun onInventoryOpen(e: InventoryOpenEvent) {
        val inventory = e.inventory
        val player = e.player
        chestInventories[player.uniqueId] = collapse(inventory.contents)
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val inventory = e.inventory
        val player = e.player
        when (inventory.type) {
            InventoryType.CHEST, InventoryType.BARREL, InventoryType.SHULKER_BOX -> {
                val opened = chestInventories[e.player.uniqueId]!!
                val closed = collapse(e.inventory.contents)
                val log = InventoryInteractEvent(player.name, inventory)

                for (mat in opened.keys) {
                    opened[mat]?.let {amountOpened -> {
                        val amountClosed = closed[mat]
                        if (amountClosed == null) {
                            log.remove(amountOpened, mat)
                        } else {
                            if (amountClosed < amountOpened) {
                                log.remove(amountClosed - amountOpened, mat)
                            } else if (amountClosed > amountOpened) {
                                log.add(amountOpened - amountClosed, mat)
                            }
                            closed.remove(mat)
                        }
                    }}

                }
                for (mat in closed.keys) {
                    log.add(closed[mat]!!, mat)
                }
                log.log()
            }

            else -> {}
        }
    }
}

/**
 * Take a list of item stacks and reduce them down to the number of each material
 * */
private fun collapse(original: Array<ItemStack>): EnumMap<Material, Int> =
    original
        // Remove any null items
        .filterNotNull()
        // Group by material
        .groupBy { it.type }
        // Convert from list of List<ItemStack> to counts
        .map { (type, stacks: List<ItemStack>) -> type to stacks.fold(0) { acc, it -> acc + it.amount } }
        // Create HashMap
        .toMap(EnumMap(org.bukkit.Material::class.java))
