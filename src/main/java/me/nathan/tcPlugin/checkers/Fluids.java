package me.nathan.tcPlugin.checkers;

import me.nathan.tcPlugin.player_logger.PlayerLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import static me.nathan.tcPlugin.TCPlugin.logger;

public class Fluids implements Listener {
	@EventHandler
	public void onFluidPace(PlayerBucketEmptyEvent e) {
		String mat = e.getBucket().toString();
		logger.info(e.getPlayer().getName() + " placed " + mat + " at " + e.getBlock().getLocation());
		PlayerLogger.logBlockPlace(e.getPlayer().getUniqueId(), mat, e.getBlock().getLocation());
	}

	@EventHandler
	public void onFluidPace(PlayerBucketFillEvent e) {
		String mat = e.getBucket().toString();
		logger.info(e.getPlayer().getName() + " picked up " + mat + " at " + e.getBlock().getLocation());
		PlayerLogger.logBlockPlace(e.getPlayer().getUniqueId(), mat, e.getBlock().getLocation());
	}
}
