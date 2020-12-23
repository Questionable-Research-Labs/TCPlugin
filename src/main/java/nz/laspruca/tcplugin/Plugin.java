package nz.laspruca.tcplugin;

import nz.laspruca.tcplugin.commands.*;
import nz.laspruca.tcplugin.eventlistners.*;
import nz.laspruca.tcplugin.logger.*;
import nz.laspruca.tcplugin.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.qrl.tcplugin.annotations.*;
import org.reflections.*;

import java.lang.reflect.*;
import java.util.*;

import static org.qrl.tcplugin.TCPlugin.*;

@TCPluginComponent
public class Plugin {
	public static Discord discord;
	public static boolean baton;

	@TCPluginComponentInit
	public static void onEnable() {
		PlayerLogger.init();
		discord = new Discord();
		plugin.registerEvent(new PlayerJoinLeave());
		plugin.registerEvent(new InventoryInteract());
		plugin.registerEvent(new BlockPlaceBreak());
		BlockPlaceBreak.loadBlocks();


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
					ex.printStackTrace();
				}
			}
		}.runTaskTimerAsynchronously(plugin, 36_000, 36_000);

		registerCommands();
	}

	@TCPluginComponentShutdown
	public static void onDisable() {
		try {
			discord.exitDiscord();
		} catch (java.lang.InterruptedException ex) {
			logger.severe("Could not shutdown discord");
		}
	}

	public static void registerCommands() {
		Reflections reflections = new Reflections("nz.laspruca");
		Set<Class<? extends CommandExecutor>> annotated = reflections.getSubTypesOf(CommandExecutor.class);

		for (Class<? extends CommandExecutor> command : annotated) {
			try {
				Constructor<?> cons = command.getConstructors()[0];
				Command commandAnnotation = command.getAnnotation(Command.class);
				plugin.getCommand(commandAnnotation.name()).setExecutor((CommandExecutor) cons.newInstance());
			} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
