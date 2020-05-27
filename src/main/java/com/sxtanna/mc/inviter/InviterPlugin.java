package com.sxtanna.mc.inviter;

import com.sxtanna.mc.inviter.discord.InviterDiscord;
import com.sxtanna.mc.inviter.options.InviterOptions;
import org.bukkit.plugin.java.JavaPlugin;

public final class InviterPlugin extends JavaPlugin
{

	private final InviterOptions options = new InviterOptions();
	private final InviterDiscord discord = new InviterDiscord(this);


	@Override
	public void onLoad()
	{
		saveDefaultConfig();

		getOptions().setConfig(getConfig());
	}

	@Override
	public void onEnable()
	{
		getDiscord().load();
	}

	@Override
	public void onDisable()
	{
		getDiscord().kill();
	}

	@Override
	public void reloadConfig()
	{
		super.reloadConfig();

		getOptions().setConfig(getConfig());

		getDiscord().kill();
		getDiscord().load();
	}


	public InviterOptions getOptions()
	{
		return options;
	}

	public InviterDiscord getDiscord()
	{
		return discord;
	}

}
