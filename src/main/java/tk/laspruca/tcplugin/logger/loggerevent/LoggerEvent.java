package tk.laspruca.tcplugin.logger.loggerevent;

import tk.laspruca.tcplugin.logger.PlayerLogger;
import tk.laspruca.tcplugin.util.DateGetter;

public abstract class LoggerEvent {
	protected String result;
	protected final String player;

	public LoggerEvent(String playerName) {
		player = playerName;
		result = "{" + DateGetter.getDate() + "}";
	}

	public void log() {
		PlayerLogger.logEvent(this.player, this.result);
	}
}
