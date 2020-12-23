package nz.laspruca.tcplugin.eventlistners;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

import java.util.*;

import static nz.laspruca.tcplugin.Plugin.hardcore;
import static org.qrl.tcplugin.TCPlugin.*;

public class PlayerDie implements Listener {
	@EventHandler
	public static void onPlayerDie(PlayerDeathEvent e) {
		if (hardcore) {
			HumanEntity player = e.getEntity();


			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tempban " + player.getName() + " 12h You died");
		}
	}
}
