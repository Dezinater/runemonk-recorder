package com.runemonk;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("runemonk")
public interface RuneMonkRecorderConfig extends Config
{
	@ConfigItem(
			keyName = "includeUsername",
			name = "Include Username in Metainfo",
			description = "Disabling this will not remove all instances of your username from the recording, only from the metainfo"
	)
	default boolean includeUsername()
	{
		return true;
	}


	@ConfigItem(
			keyName = "prettyPrint",
			name = "Pretty Print RSREC",
			description = "Makes the JSON output in the .rsrec file pretty"
	)
	default boolean prettyPrint()
	{
		return false;
	}

	//possibly have an option to record bitmaps as binary strings
	//makes it easier for people to modify recordings
}
