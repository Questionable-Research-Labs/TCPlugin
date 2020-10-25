package nz.laspruca.tcplugin.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.qrl.tcplugin.TCPlugin.plugin;

public class PlayerLogger {
	public static void logEvent(String playerName, String msg) {
		File f = new File(plugin.getDataFolder() + String.format("/logs/%s.txt", playerName));
		try {
			if (!f.exists()) {
				if (!f.createNewFile()) {
					return;
				}
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write(msg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
