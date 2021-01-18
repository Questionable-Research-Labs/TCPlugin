package nz.laspruca.tcplugin.eventlistners;

import nz.laspruca.tcplugin.logger.loggerevent.InventoryInteractEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.util.*;

public class InventoryInteract implements Listener {
	static HashMap<UUID, HashMap<Material, Integer>> chestInventories = new HashMap<>();

	@EventHandler
	public static void onInventoryOpen(InventoryOpenEvent e) {
		Inventory inventory = e.getInventory();
		HumanEntity player = e.getPlayer();
		chestInventories.put(player.getUniqueId(), collapse(Arrays.asList(inventory.getContents())));
	}

	@EventHandler
	public static void onInventoryClose(InventoryCloseEvent e) {
		Inventory inventory = e.getInventory();
		HumanEntity player = e.getPlayer();

		switch (inventory.getType()) {
			case CHEST:
			case BARREL:
			case SHULKER_BOX:
				HashMap<Material, Integer> opened = chestInventories.get(e.getPlayer().getUniqueId());
				HashMap<Material, Integer> closed = collapse(Arrays.asList(e.getInventory().getContents()));

				nz.laspruca.tcplugin.logger.loggerevent.InventoryInteractEvent log = new InventoryInteractEvent(player.getName(), inventory);

				for (Material mat : opened.keySet()) {
					Integer amountOpened = opened.get(mat);
					Integer amountClosed = closed.get(mat);

					if (amountClosed == null) {
						log.remove(amountOpened, mat);
					} else {
						if (amountClosed < amountOpened) {
							log.remove(amountClosed - amountOpened, mat);
						} else if (amountClosed > amountOpened) {
							log.add(amountOpened - amountClosed, mat);
						}
						closed.remove(mat);
					}
				}

				for (Material mat : closed.keySet()) {
					log.add(closed.get(mat), mat);
				}

				log.log();

				break;
			default:
				break;
		}
	}


	public static HashMap<Material, Integer> collapse(List<ItemStack> original) {
		HashMap<Material, Integer> result = new HashMap<>();

		for (ItemStack a : original) {
			if (a != null) {
				result.put(a.getType(),
						(result.get(a.getType()) == null ? 0 : result.get(a.getType())) + a.getAmount());
			}
		}

		return result;
	}
}
