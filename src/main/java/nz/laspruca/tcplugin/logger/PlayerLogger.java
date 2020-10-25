package nz.laspruca.tcplugin.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.qrl.tcplugin.TCPlugin.logger;
import static org.qrl.tcplugin.TCPlugin.plugin;

public class PlayerLogger {
	public static void init() {
		new File(plugin.getDataFolder() + "/logs").mkdir();
	}

	public static void logEvent(String playerName, String msg) {
		File f = new File(plugin.getDataFolder() + String.format("/logs/%s.txt", playerName));
		try {
			f.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
			writer.write("\n" + msg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
