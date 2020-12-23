package nz.laspruca.tcplugin.eventlistners;

import nz.laspruca.tcplugin.logger.loggerevent.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

import java.util.*;

import static org.qrl.tcplugin.TCPlugin.config;
import static org.qrl.tcplugin.TCPlugin.logger;

public class BlockPlaceBreak implements Listener {
	public static List<Material> breakBlocks = new ArrayList<>();
	public static List<Material> placeBlocks = new ArrayList<>();

	@EventHandler
	public static void onPlayerBreakBlock(BlockBreakEvent e) {
		HumanEntity player = e.getPlayer();
		Block block = e.getBlock();

		if (breakBlocks.contains(block.getType())) {
			new BreakBlockEvent(player.getName(), block).log();
		}
	}

	@EventHandler
	public static void onPlayerPlaceBlock(BlockPlaceEvent e) {
		HumanEntity player = e.getPlayer();
		Block block = e.getBlock();

		if (placeBlocks.contains(block.getType())) {
			new PlaceBlockEvent(player.getName(), block).log();
		}
	}


	public static void loadBlocks() {
		List<String> _break = config.getStringList("blocks-break");
		for (String a : _break) {
			try {
				breakBlocks.add(Material.valueOf(a.toUpperCase()));
			} catch (Exception e) {
				logger.warning("Invalid block in config " + a);
			}
		}


		breakBlocks.add(Material.CHEST);

		List<String> place = config.getStringList("place-break");
		for (String a : place) {
			try {
				placeBlocks.add(Material.valueOf(a.toUpperCase()));
			} catch (Exception e) {
				logger.warning("Invalid block in config " + a);
			}
		}
	}
}

