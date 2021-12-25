package com.runemonk;

import com.google.gson.JsonObject;
import com.google.inject.Provides;
import javax.inject.Inject;

import com.runemonk.output.FileWriter;
import lombok.extern.slf4j.*;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class RuneMonkRecorder extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private RuneMonkRecorderConfig config;

	@Inject
	private EventBus eventBus;

	private EventSubscribers eventSubscribers;

	@Override
	protected void startUp() throws Exception
	{
		log.info(client.getTickCount() + " RuneMonk Recorder started!");
		eventSubscribers = new EventSubscribers(client, new FileWriter(client));
		eventBus.register(eventSubscribers);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("RuneMonk Recorder stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}


	@Provides
	RuneMonkRecorderConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RuneMonkRecorderConfig.class);
	}
}
