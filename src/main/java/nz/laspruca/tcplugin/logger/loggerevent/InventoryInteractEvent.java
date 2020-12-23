package nz.laspruca.tcplugin.logger.loggerevent;

import org.bukkit.*;
import org.bukkit.inventory.*;

public class InventoryInteractEvent extends LoggerEvent {

	public InventoryInteractEvent(String playerName, Inventory inv) {
		super(playerName);

		result += "Modify" + inv.getType().toString() +
				" @ {X: " + inv.getLocation().getBlockX() +
				", Y: " + inv.getLocation().getBlockY() +
				", Z: " + inv.getLocation().getBlockX() + "}" + "\n";
	}

	public void add(int amount, Material type) {
		result += "\tAdded " + amount + " " + type.name() + "\n";
	}

	public void remove(int amount, Material type) {
		result += "\tRemoved " + amount + " " + type.name();
	}
}
