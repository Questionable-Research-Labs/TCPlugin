package nz.laspruca.tcplugin.util;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Member;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.qrl.tcplugin.TCPlugin.*;

public class Discord {
	public GatewayDiscordClient gateway;
	private boolean goBrr = true;

	public Discord(String token) {
//		DiscordClient client = DiscordClient.builder(token).build();
//		gateway = client.login().block();
//		if (gateway == null) {
//			logger.warning("Unable to connect to discord, oh fucking well");
//			goBrr = false;
//		}
		goBrr = false;
		logger.info("Discord has been disabled due to problems with Discord4J");
	}

	public void exitDiscord() {
		if (goBrr) {
			gateway.logout();
		}
	}

	public boolean logedIn() {
		return this.goBrr;
	}

	public List<String> getMembers() throws IllegalStateException {
		if (goBrr)
			return Objects.requireNonNull(Objects.requireNonNull(gateway
					.getGuilds()
					.collect(Collectors.toList())
					.block())
					.get(0)
					.getMembers()
					.collect(Collectors.toList())
					.block())
					.stream()
					.map(Member::getDisplayName)
					.collect(Collectors.toList());
		else
			throw new IllegalStateException("No valid connection to discord");
	}
}
