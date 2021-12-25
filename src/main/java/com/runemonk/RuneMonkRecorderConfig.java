package com.runemonk;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("runemonk")
public interface RuneMonkRecorderConfig extends Config
{
	@ConfigItem(
		keyName = "runemonk,record,video,film",
		name = "RuneMonk Recorder",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}
}
