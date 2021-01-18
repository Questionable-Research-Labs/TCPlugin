package nz.laspruca.tcplugin.commands;

import org.bukkit.command.*;
import org.jetbrains.annotations.*;

@nz.laspruca.tcplugin.Command(name = "bee")
public class Bee implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
		commandSender.sendMessage("According to all known laws of aviation, there is no way that a bee should be able to fly.");
		return true;
	}
}
