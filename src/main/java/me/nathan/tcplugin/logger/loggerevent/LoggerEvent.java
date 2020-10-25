package me.nathan.tcplugin.logger.loggerevent;

import me.nathan.tcplugin.logger.PlayerLogger;
import me.nathan.tcplugin.util.DateGetter;

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
