package com.sxtanna.mc.inviter;

import com.sxtanna.mc.inviter.command.InviterCommand;
import com.sxtanna.mc.inviter.options.InviterMessage;
import com.sxtanna.mc.inviter.discord.InviterDiscord;
import com.sxtanna.mc.inviter.options.InviterOptions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class InviterPlugin extends JavaPlugin
{

	private final InviterOptions options = new InviterOptions();
	private final InviterCommand command = new InviterCommand(this);
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
	}


	public InviterCommand getCommand()
	{
		return command;
	}

	public InviterOptions getOptions()
	{
		return options;
	}

	public InviterDiscord getDiscord()
	{
		return discord;
	}


	public void reload()
	{
		reloadConfig();

		getDiscord().kill();
		getDiscord().load();
	}


	public void reply(final CommandSender sender, final String message)
	{
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public void reply(final CommandSender sender, final InviterMessage message, final Object... placeholders)
	{
		if (placeholders.length % 2 != 0)
		{
			throw new IllegalArgumentException("Placeholders must all have values: " + Arrays.toString(placeholders));
		}

		String text = getOptions().getMessage(message);

		if (placeholders.length != 0)
		{
			for (int i = 0; i < placeholders.length; i += 2)
			{
				final Object k = placeholders[i];
				final Object v = placeholders[i + 1];

				if (!(k instanceof String))
				{
					throw new IllegalArgumentException("Placeholder values has an object out of position: " + k.getClass() + "[" + i + "]{" + k + "}");
				}

				text = text.replace("%" + k + "%", v.toString());
			}
		}

		reply(sender, text);
	}

}
