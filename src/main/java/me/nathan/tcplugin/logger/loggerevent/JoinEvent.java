package me.nathan.tcplugin.logger.loggerevent;

import java.util.Date;
import java.util.logging.Logger;

public class JoinEvent extends LoggerEvent {

	public JoinEvent(String name) {
		super(name);
		result += "Joined Game";
	}
}
