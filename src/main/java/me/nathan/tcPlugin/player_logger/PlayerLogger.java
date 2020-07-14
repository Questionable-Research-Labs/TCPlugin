package me.nathan.tcPlugin.player_logger;

import org.bukkit.*;
import me.nathan.tcPlugin.TCPlugin;
import org.bukkit.event.inventory.InventoryType;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

import static me.nathan.tcPlugin.TCPlugin.logger;

public class PlayerLogger {
    static TCPlugin plugin = TCPlugin.getPlugin(TCPlugin.class);

    public static HashMap<UUID, File> playerLogs = new HashMap<>();

    public static void createLogFile(UUID playerID) {
        File file = new File(plugin.getDataFolder() + "/logs/", plugin.getServer().getPlayer(playerID).getName() + ".txt");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.severe("Unable to crete log file for " + plugin.getServer().getPlayer(playerID).getName());
            }
        }
        playerLogs.put(playerID, file);
    }

    public static void logData(UUID playerID, String message) {
        File file = playerLogs.get(playerID);
        LocalDateTime date = LocalDateTime.now();
        String dateS = date.toString();
        dateS = dateS.replace("T", " ");
        if(!file.canWrite()) {
            logger.severe("Can not write to " + plugin.getServer().getPlayer(playerID).getName() + "'s log file");
            return;
        }
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(("\n[" + dateS + "] " + message).toCharArray());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            logger.severe("Error creating file writer");
        }
    }

    public static void logBlockBreak(UUID playerID, Material type, Location location) {
        String message = type.name() + " was broken at X:" + location.getBlockX() + " Y:" + location.getBlockY() + " Z:" + location.getBlockZ() + " in "
                + location.getWorld().getEnvironment().name();
        logData(playerID, message);
    }

    public static void logBlockBreak(UUID playerID, String type, Location location) {
        String message = type + " was broken at X:" + location.getBlockX() + " Y:" + location.getBlockY() + " Z:" + location.getBlockZ() + " in "
                + location.getWorld().getEnvironment().name();
        logData(playerID, message);
    }

    public static void logBlockPlace(UUID playerID, Material type, Location location) {
        String message = type.name() + " was placed at X:" + location.getBlockX() + " Y:" + location.getBlockY() + " Z:" + location.getBlockZ() + " in "
                + location.getWorld().getEnvironment().name();
        logData(playerID, message);
    }

    public static void logBlockPlace(UUID playerID, String type, Location location) {
        String message = type + " was placed at X:" + location.getBlockX() + " Y:" + location.getBlockY() + " Z:" + location.getBlockZ() + " in "
                + location.getWorld().getEnvironment().name();
        logData(playerID, message);
    }

    public static void logChestModify(UUID playerID, InventoryType type, Location location, String changes) {
        String message = type.name() + " modified at X:" + location.getBlockX() + " Y:" + location.getBlockY() + " Z:" + location.getBlockZ() + " in "
                + location.getWorld().getEnvironment().name() + "\n" + changes;
        logData(playerID, message);
    }

    public static void closeLogFile(UUID playerID) {
        playerLogs.remove(playerID);
    }
}
