package com.sxtanna.mc.inviter.options;

import org.bukkit.configuration.ConfigurationSection;

public final class InviterOptions
{

	private static final int    DEF_COMMAND_COOLDOWN = 600;
	private static final String DEF_COMMAND_REQUIRES = "inviter.command";

	private static final long DEF_DISCORD_CHANNEL        = -1L;
	private static final int  DEF_DISCORD_INVITES_USAGES = 1;
	private static final int  DEF_DISCORD_INVITES_EXPIRE = 300;


	private ConfigurationSection config;

	public void setConfig(final ConfigurationSection config)
	{
		this.config = config;
	}


	public int getCommandCooldown()
	{
		return config != null ? config.getInt("command.cooldown", DEF_COMMAND_COOLDOWN) : DEF_COMMAND_COOLDOWN;
	}

	public String getCommandRequires()
	{
		return config != null ? config.getString("command.requires", DEF_COMMAND_REQUIRES) : DEF_COMMAND_REQUIRES;
	}

	public String getDiscordToken()
	{
		return config != null ? config.getString("discord.token", "") : "";
	}

	public long getDiscordGuild()
	{
		return config != null ? config.getLong("discord.guild", -1) : -1;
	}

	public long getDiscordChannel()
	{
		return config != null ? config.getLong("discord.channel", DEF_DISCORD_CHANNEL) : DEF_DISCORD_CHANNEL;
	}

	public int getDiscordInvitesUsages()
	{
		return config != null ? config.getInt("discord.invites.usages", DEF_DISCORD_INVITES_USAGES) : DEF_DISCORD_INVITES_USAGES;
	}

	public int getDiscordInvitesExpire()
	{
		return config != null ? config.getInt("discord.invites.expire", DEF_DISCORD_INVITES_EXPIRE) : DEF_DISCORD_INVITES_EXPIRE;
	}

}
