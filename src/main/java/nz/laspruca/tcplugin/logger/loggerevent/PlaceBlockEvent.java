package nz.laspruca.tcplugin.logger.loggerevent;

import org.bukkit.block.*;

public class PlaceBlockEvent extends LoggerEvent {
	public PlaceBlockEvent(String playerName, Block block) {
		super(playerName);

		result += "Break " + block.getType() +
				" @ {X: " + block.getLocation().getBlockX() +
				", Y: " + block.getLocation().getBlockY() +
				", Z: " + block.getLocation().getBlockX() + "}";
	}
}
