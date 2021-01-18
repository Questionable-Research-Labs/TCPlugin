package nz.laspruca.tcplugin;

import nz.laspruca.tcplugin.commands.*;
import nz.laspruca.tcplugin.discord.*;
import nz.laspruca.tcplugin.eventlistners.*;
import nz.laspruca.tcplugin.logger.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.qrl.tcplugin.annotations.*;
import org.reflections.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import static org.qrl.tcplugin.TCPlugin.*;

/**
 * Main class for Nathan's component
 */
@TCPluginComponent
public class Plugin {
	public static Discord discord;
	public static boolean baton;
	public static boolean hardcore;
	public static boolean announceDeath;

	/**
	 * Entery point for Nathan's component
	 */
	@TCPluginComponentInit
	public static void onEnable() {
		PlayerLogger.init();

		discord = new Discord();

		plugin.registerEvent(new PlayerJoinLeave());
		plugin.registerEvent(new InventoryInteract());
		plugin.registerEvent(new BlockPlaceBreak());
		plugin.registerEvent(new PlayerDie());

		BlockPlaceBreak.loadBlocks();

		baton = config.getBoolean("givebaton");
		hardcore = config.getBoolean("hardcore");
		announceDeath = config.getBoolean("announceDeath");

		// Create event to notify people that they can join the discord if they are not already
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					List<String> discordUsers = discord.getMembers();

					for (Player player : plugin.getServer().getOnlinePlayers()) {
						if (!discordUsers.contains(player.getDisplayName())) {
							player.spigot().sendMessage(DiscordCommand.generateText());
						}
					}
				} catch (IllegalStateException ex) {
					// This happens if discord failed to init
					ex.printStackTrace();
				}
			}
		}.runTaskTimerAsynchronously(plugin, 36_000, 36_000);

		registerCommands();
	}

	/**
	 * Shutdown logic for component
	 */
	@TCPluginComponentShutdown
	public static void onDisable() {
		// update and save config
		config.set("givebaton", baton);
		config.set("hardcore", hardcore);
		config.set("announceDeath", announceDeath);

		try {
			config.save(plugin.getDataFolder() + "/config.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Shutdown discord
		try {
			discord.exitDiscord();
		} catch (java.lang.InterruptedException ex) {
			logger.severe("Could not shutdown discord");
		}
	}

	/**
	 * Loads all commands in the nz.laspruca package so I don't have to do it manually
	 */
	public static void registerCommands() {
		Reflections reflections = new Reflections("nz.laspruca");
		Set<Class<? extends CommandExecutor>> annotated = reflections.getSubTypesOf(CommandExecutor.class);

		for (Class<? extends CommandExecutor> command : annotated) {
			try {
				Constructor<?> cons = command.getConstructors()[0];
				Command commandAnnotation = command.getAnnotation(Command.class);
				Objects.requireNonNull(plugin.getCommand(commandAnnotation.name())).setExecutor((CommandExecutor) cons.newInstance());
			} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
