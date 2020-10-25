package nz.laspruca.tcplugin.logger.loggerevent;

import nz.laspruca.tcplugin.logger.PlayerLogger;
import nz.laspruca.tcplugin.util.DateGetter;

public abstract class LoggerEvent {
	protected String result;
	protected final String player;

	public LoggerEvent(String playerName) {
		player = playerName;
		result = "[" + DateGetter.getDate() + "] ";
	}

	public void log() {
		PlayerLogger.logEvent(this.player, this.result);
	}
}
