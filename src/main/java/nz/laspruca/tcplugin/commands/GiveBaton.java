package nz.laspruca.tcplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static nz.laspruca.tcplugin.Plugin.baton;
import static org.qrl.tcplugin.TCPlugin.*;

import java.util.Objects;

@nz.laspruca.tcplugin.Command(name = "givebaton")
public class GiveBaton implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		if (baton) {
			int argslen = 0;

			for (String str : args) {
				argslen++;
			}

			if (argslen < 1) {
				Objects.requireNonNull(plugin.getServer().getPlayer(commandSender.getName())).getInventory().addItem(makeBonkyBoi());
			} else {
				for (String str : args) {
					if (str.equals("@a")) {
						for (Player player : plugin.getServer().getOnlinePlayers()) {
							player.getInventory().addItem(makeBonkyBoi());
						}
					} else if (str.equals("@p")) {
						Objects.requireNonNull(plugin.getServer().getPlayer(commandSender.getName())).getInventory().addItem(makeBonkyBoi());
					} else {
						try {
							Objects.requireNonNull(plugin.getServer().getPlayer(str)).getInventory().addItem(makeBonkyBoi());
						} catch (Exception ignored) {
							commandSender.sendMessage("Unable to find player " + str);
						}
					}
				}
			}
		}

		commandSender.sendMessage(
				ChatColor.RED.toString() +
						ChatColor.BOLD.toString() +
						"GIVE BATON HAD BEEN DISABLED BY ORDER OF THE 05 COUNCIL");

		return true;
	}

	private ItemStack makeBonkyBoi() {
		ItemStack is = new ItemStack(Material.STICK, 1);

		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();

		lore.add("THIS IS YOUR OFFICIAL TECHNOCRAFT POLICE DPT BATON");
		lore.add("HAVE FUN");
		im.setLore(lore);
		im.addEnchant(Enchantment.KNOCKBACK, 1000, true);
		im.setDisplayName(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "TCPD Baton");

		is.setItemMeta(im);

		return is;
	}
}
