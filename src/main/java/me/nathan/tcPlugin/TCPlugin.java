package me.nathan.tcPlugin;

import me.nathan.tcPlugin.chat.FidgetSpinner;
import me.nathan.tcPlugin.checkers.*;
import me.nathan.tcPlugin.commands.*;
import me.nathan.tcPlugin.player_logger.*;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TCPlugin extends JavaPlugin{
    public static Logger logger;
    public static ArrayList<Material> blocksBreak = new ArrayList<>();
    public static ArrayList<Material> blocksPlcae = new ArrayList<>();

    @Override
    public void onEnable() {
        // Create the config files
        saveDefaultConfig();

        // Get the logger
        logger = getLogger();

        // Register Commands
        Objects.requireNonNull(getCommand("givebaton")).setExecutor(new GiveBaton());
        Objects.requireNonNull(getCommand("bee")).setExecutor(new me.nathan.tcPlugin.commands.Bee());
        Objects.requireNonNull(getCommand("discord")).setExecutor(new Discord());

        // Load blocks from config file
        blocksBreak = loadBlocksFromConfig("blocks-break");
        blocksPlcae = loadBlocksFromConfig("blocks-placed");

        // Register event managers
        registerEvent(new Blocks());
        registerEvent(new Chests());
        registerEvent(new PlayerJoin());
        registerEvent(new Fluids());
        registerEvent(new Eggnog());
        registerEvent(new FidgetSpinner());

        // Create an event that will give the discord link to all players
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player player : getServer().getOnlinePlayers()) {
                    player.spigot().sendMessage(Discord.generateText());
                }
            }
        }.runTaskTimer(this, 50, 36000);

        logger.info("Started TechnocraftPlugin");
    }

    private ArrayList<Material> loadBlocksFromConfig(String path) {
        int i = 0;
        ArrayList<Material> blks = new ArrayList<>();
        List<String> ls = getConfig().getStringList(path);
        for (String s : ls) {
            try {
                i++;
                Material m = Material.valueOf(s.toUpperCase());
                blks.add(m);
            } catch (Exception e) {
                logger.info("Entry " + s + " at " + i + " is invalid");
            }
        }
        return blks;
    }

    @Override
    public void onDisable() {
        PlayerLogger.playerLogs.clear();
    }

    private void registerEvent(Listener e) {
        getServer().getPluginManager().registerEvents(e, this);
    }

}
