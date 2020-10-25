package nz.laspruca.tcplugin.logger.loggerevent;

public class LeaveEvent extends LoggerEvent {

	public LeaveEvent(String name) {
		super(name);
		result += "Left Game";
	}
}
