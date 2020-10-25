package me.nathan.tcplugin.util;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Member;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static me.nathan.tcplugin.TCPlugin.*;

public class Discord {
	public GatewayDiscordClient gateway;
	private boolean goBrr = true;

	public Discord(String token) {
		gateway = DiscordClient.create(token).login().block();
		if (gateway == null) {
			LOGGER.warning("Unable to connect to discord, oh fucking well");
			goBrr = false;
		}
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
