package com.sxtanna.mc.inviter.command;

import com.sxtanna.mc.inviter.InviterPlugin;
import com.sxtanna.mc.inviter.options.InviterMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.threeten.extra.AmountFormats;

import java.time.Duration;
import java.util.Locale;

public final class InviterCommand implements CommandExecutor
{

	private final InviterPlugin plugin;
	private final NamespacedKey namespace;


	public InviterCommand(final InviterPlugin plugin)
	{
		this.plugin    = plugin;
		this.namespace = new NamespacedKey(plugin, "cooldown");
	}


	public void load()
	{
		final PluginCommand command = plugin.getCommand("discord");
		if (command != null)
		{
			command.setExecutor(this);
		}
	}

	public void kill()
	{
		final PluginCommand command = plugin.getCommand("discord");
		if (command != null)
		{
			command.setExecutor(null);
		}
	}


	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String alias, @NotNull final String[] args)
	{
		if (!sender.hasPermission(plugin.getOptions().getCommandRequires()))
		{
			plugin.reply(sender, InviterMessage.INVITE_FAIL_NEED_PERM,

						 "permission",
						 plugin.getOptions().getCommandRequires());

			return true;
		}

		if (sender instanceof Player)
		{
			final Player player = (Player) sender;

			if (isOnCooldown(player))
			{
				return true;
			}
		}

		plugin.getDiscord().generateInvite(sender);

		return true;
	}


	private boolean isOnCooldown(final Player player)
	{
		final long curr = System.currentTimeMillis();
		final Long next = player.getPersistentDataContainer().get(namespace, PersistentDataType.LONG);

		if (next != null && curr < next)
		{
			plugin.reply(player, InviterMessage.INVITE_FAIL_MUST_WAIT,

						 "time",
						 AmountFormats.wordBased(Duration.ofMillis(next - curr), Locale.forLanguageTag(player.getLocale())));
			return true;
		}

		player.getPersistentDataContainer().set(namespace, PersistentDataType.LONG, curr + plugin.getOptions().getCommandCooldown());
		return false;
	}

}
