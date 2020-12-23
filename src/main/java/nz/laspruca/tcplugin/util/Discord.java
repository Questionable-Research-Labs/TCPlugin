package nz.laspruca.tcplugin.util;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.hooks.*;
import net.dv8tion.jda.api.requests.*;
import net.dv8tion.jda.api.utils.cache.*;
import org.jetbrains.annotations.*;

import javax.security.auth.login.*;
import java.util.*;
import java.util.stream.*;

import static org.qrl.tcplugin.TCPlugin.*;

public class Discord extends ListenerAdapter {
	public JDA jda;
	private boolean goBrr = false;

	public Discord() {
		logger.info("Starting Discord");
		if (config.getString("prefix") == null) {
			logger.warning("No prefix set, setting to default");
			config.set("prefix", "setme");
		}
		try {
			jda = JDABuilder.createDefault(config.getString("discordToken"))
					.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.setActivity(Activity.of(Activity.ActivityType.WATCHING, "TechnoCraft Servers"))
					.addEventListeners(this, new DiscordMessageListener())
					.build();
		} catch (LoginException ex) {
			logger.warning("Unable to initialize discord api, invalid login token");
		}
	}

	public void exitDiscord() throws InterruptedException {
		if (goBrr) {
			sendMessage("Shutting down");

			jda.shutdown();
		}
	}

	public boolean logedIn() {
		return this.goBrr;
	}

	public List<String> getMembers() throws IllegalStateException {
		if (goBrr) {
			return jda
					.getGuildsByName("technocraft", true)
					.get(0)
					.loadMembers()
					.get()
					.stream()
					.map(a -> a.getNickname() == null ? a.getUser().getName() : a.getNickname())
					.collect(Collectors.toList());
		} else {
			throw new IllegalStateException("No valid connection to discord");
		}
	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		super.onReady(event);

		goBrr = true;

		sendMessage("Server started");
	}


	public void sendMessage(String message) {
		sendMessage(new EmbedBuilder().addField(message, "", true));
	}

	public void sendMessage(EmbedBuilder message) {
		if (goBrr) {
			Objects.requireNonNull(jda.getGuildsByName("technocraft", true)
					.get(0)
					.getTextChannelById("791082936609931264"))
					.sendMessage(message.setAuthor(config.getString("prefix")).build())
					.queue();
		}
	}
}

