package nz.laspruca.tcplugin.discord;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import static org.qrl.tcplugin.TCPlugin.*;
import static nz.laspruca.tcplugin.Plugin.*;

public class DiscordMessageListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		super.onMessageReceived(event);

		String message = event.getMessage().getContentStripped();

		if (!event.getAuthor().isBot() && message.startsWith("\\") && event.getChannel().getId().equals("791082936609931264")) {
			List<String> splits = Arrays.asList(message.split(" "));

			if (splits.size() > 1 && splits.get(0).equals("\\" + config.getString("prefix"))) {
				String command = splits.get(1);
				List<String> args = splits.stream().skip(2).collect(Collectors.toList());
				switch (command) {
					case "say":
						if (args.size() > 0) {
							plugin.getServer().broadcastMessage(
									String.join(" ", args)
							);
							discord.sendMessage("Saying: ");
						} else {
							discord.sendMessage("Expected at least one argument, message");
						}
						break;

					case "prefix":
						if (args.size() != 1) {
							discord.sendMessage("Expected at one argument, name");
						} else {
							config.set("prefix", args.get(0));
							try {
								config.save(plugin.getDataFolder() + "/config.yml");
							} catch (IOException e) {
								e.printStackTrace();
							}
							discord.sendMessage("Setting prefix to " + args.get(0));
						}
						break;

					case "shutdown":
						plugin.getServer().shutdown();
						break;

					case "online":
						Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
						discord.sendMessage(
								new EmbedBuilder()
										.setTitle(plugin.getServer().getOnlinePlayers().size() + " Online")
										.addField("Players ", onlinePlayers.stream().map(Player::getDisplayName).collect(Collectors.joining(", ")), false)
						);
						break;

					case "info":
						discord.sendMessage(new EmbedBuilder()
								.setTitle("Info for " + config.getString("prefix"))
								.addField("/givebaton", baton ? "Enabled" : "Disabled", true)
								.addField("pseudo-hardcore mode", hardcore ? "Enabled" : "Disabled", true)
								.addField("Death announcements", announceDeath ? "Enabled" : "Disabled", true));
						break;

					case "t_givebaton":
						baton = !baton;
						discord.sendMessage(baton ? "Enable /givebaton" : "Disabled /givebaton");
						break;

					case "t_hardcore":
						hardcore = !hardcore;
						discord.sendMessage(hardcore ? "Server is in Hardcore mode" : "Server is in normal mode");
						break;

					case "t_deathmsg":
						announceDeath = !announceDeath;
						discord.sendMessage(announceDeath ? "Enable death messages" : "Disabled death messages");
						break;

					case "help":
						discord.sendMessage(new EmbedBuilder()
								.setTitle("Help for TCPlugin discord commands")
								.addField("Syntax", "\\[server_prefix] [command] <args>", false)
								.addBlankField(false)
								.addField("shutdown", "Shutdown server", false)
								.addField("prefix", "Sets the servers prefix\n\t**- 1)** the new prefix", false)
								.addField("say", "Sends a message to the server\n\t**- 1..∞)** the message", false)
								.addField("online", "Shows all the online players", false)
								.addField("info", "Shows info about the server", false)
								.addField("t_givebaton", "Enables/disables /givebaton", false)
								.addField("t_hardcore", "Enables/disables pseudo-hardcore mode", false)
								.addField("t_deathmsg", "Enables/disables announcements on death", false)
								.addField("help", "Shows this", false)
						);
						break;

					default:
						discord.sendMessage("Invalid commands");
						break;
				}
			}
		}
	}
}
