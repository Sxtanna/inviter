package com.sxtanna.mc.inviter.discord;

import com.sxtanna.mc.inviter.InviterPlugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import javax.security.auth.login.LoginException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public final class InviterDiscord
{

	private final InviterPlugin        plugin;
	private final AtomicReference<JDA> discord = new AtomicReference<>();


	public InviterDiscord(final InviterPlugin plugin)
	{
		this.plugin = plugin;
	}


	public void load()
	{
		Bukkit.getScheduler().runTaskAsynchronously(plugin, this::initializeDiscord);
	}

	public void kill()
	{
		final JDA discord = this.discord.getAndSet(null);
		if (discord != null)
		{
			discord.shutdownNow();
		}
	}


	private void initializeDiscord()
	{
		final JDA discord;
		try
		{
			discord = JDABuilder.create(plugin.getOptions().getDiscordToken(), GatewayIntent.GUILD_INVITES).build();
		}
		catch (final LoginException ex)
		{
			plugin.getLogger().log(Level.SEVERE, "discord bot could not login", ex);
			return;
		}

		try
		{
			discord.awaitReady();
		}
		catch (final InterruptedException ex)
		{
			plugin.getLogger().log(Level.SEVERE, "discord bot failed to initialize", ex);
			return;
		}

		this.discord.set(discord);

		plugin.getLogger().info("successfully loaded discord bot");
	}


	public void generateInvite(final CommandSender sender)
	{
		final JDA discord = this.discord.get();
		if (discord == null)
		{
			return;
		}

		final Guild guild = discord.getGuildById(plugin.getOptions().getDiscordGuild());
		if (guild == null)
		{
			return;
		}

		GuildChannel channel = guild.getGuildChannelById(plugin.getOptions().getDiscordChannel());
		if (channel == null)
		{
			channel = guild.getDefaultChannel();
		}

		if (channel == null)
		{
			return;
		}

		channel.createInvite()
			   .setMaxUses(plugin.getOptions().getDiscordInvitesUsages())
			   .setMaxAge(plugin.getOptions().getDiscordInvitesExpire())
			   .queue(invite -> {

				   // send invite link

			   });
	}

}
