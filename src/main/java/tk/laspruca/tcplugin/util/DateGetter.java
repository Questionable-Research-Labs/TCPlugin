package tk.laspruca.tcplugin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGetter {
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("E yyyy/MM/dd:HH:mm:ss");

	public static String getDate() {
		return FORMAT.format(new Date());
	}
}
