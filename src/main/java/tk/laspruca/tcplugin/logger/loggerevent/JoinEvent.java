package tk.laspruca.tcplugin.logger.loggerevent;

public class JoinEvent extends LoggerEvent {

	public JoinEvent(String name) {
		super(name);
		result += "Joined Game";
	}
}
