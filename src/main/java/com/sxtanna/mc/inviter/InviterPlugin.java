package com.sxtanna.mc.inviter;

import com.sxtanna.mc.inviter.options.InviterOptions;
import org.bukkit.plugin.java.JavaPlugin;

public final class InviterPlugin extends JavaPlugin
{

	private final InviterOptions options = new InviterOptions();


	@Override
	public void onLoad()
	{
		saveDefaultConfig();

		getOptions().setConfig(getConfig());
	}

	@Override
	public void onEnable()
	{

	}

	@Override
	public void onDisable()
	{

	}

	@Override
	public void reloadConfig()
	{
		super.reloadConfig();

		getOptions().setConfig(getConfig());
	}


	public InviterOptions getOptions()
	{
		return options;
	}

}
