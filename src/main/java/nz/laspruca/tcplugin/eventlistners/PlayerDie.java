package nz.laspruca.tcplugin.eventlistners;

import net.dv8tion.jda.api.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

import static nz.laspruca.tcplugin.Plugin.*;
import static org.qrl.tcplugin.TCPlugin.*;

public class PlayerDie implements Listener {
	@EventHandler
	public static void onPlayerDie(PlayerDeathEvent e) {
		HumanEntity player = e.getEntity();

		if (hardcore) {
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tempban " + player.getName() + " 12h You died");
		}

		logger.info("Announce death is " + announceDeath);

		if (announceDeath) {
			logger.info(player.getName() + " noped.");
			String reason = e.getDeathMessage();
			discord.sendMessageToChannel(new EmbedBuilder().setTitle(player.getName() + " Just died")
					.addField("Reason: ", reason, false), "800632332646088714");
		}
	}
}
