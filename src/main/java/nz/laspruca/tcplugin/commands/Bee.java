package nz.laspruca.tcplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.qrl.tcplugin.TCPlugin.logger;

@nz.laspruca.tcplugin.Command(name = "bee")
public class Bee implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		commandSender.sendMessage("According to all known laws of aviation, there is no way that a bee should be able to fly.");
		return true;
	}
}
