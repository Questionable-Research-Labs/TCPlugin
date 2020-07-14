package me.nathan.tcPlugin.checkers;


import org.bukkit.Material;
import org.bukkit.event.block.*;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import me.nathan.tcPlugin.player_logger.PlayerLogger;


import static me.nathan.tcPlugin.TCPlugin.*;

public class Blocks implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Material mat = e.getBlock().getType();
        for (Material m : blocksBreak) {
            if (m == mat) {
                logger.info(e.getPlayer().getName() + " broke " + mat.name() + " at " + e.getBlock().getLocation());
                PlayerLogger.logBlockBreak(e.getPlayer().getUniqueId(), mat, e.getBlock().getLocation());
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Material mat = e.getBlock().getType();
        for (Material m : blocksPlcae) {
            if (m == mat) {
                logger.info(e.getPlayer().getName() + " placed " + mat.name() + " as " + e.getBlock().getLocation());
                PlayerLogger.logBlockPlace(e.getPlayer().getUniqueId(), mat, e.getBlock().getLocation());
            }
        }
    }
}
