package com.sxtanna.mc.inviter.options;

public enum InviterMessage
{
	INVITE_PASS("&9Discord invite link&8: &7%invite%"),
	INVITE_FAIL_MUST_WAIT("&cYou must wait &e%time%&c before doing that again!"),
	INVITE_FAIL_NEED_PERM("&cYou do not have permission to do this!"),
	INVITE_FAIL_EXCEPTION("&cDiscord invite could not be generated: &7%reason%");


	private final String value;

	InviterMessage(final String value)
	{
		this.value = value;
	}


	public String getDefaultValue()
	{
		return value;
	}
}
