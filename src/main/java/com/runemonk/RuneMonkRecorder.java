package com.runemonk;

import com.google.gson.JsonObject;
import com.google.inject.Provides;
import javax.inject.Inject;

import com.runemonk.output.FileWriter;
import com.runemonk.output.MetaInfo;
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
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "RuneMonk Recorder"
)
public class RuneMonkRecorder extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private RuneMonkRecorderConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private EventBus eventBus;

	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton navButton;
	private RuneMonkRecorderPanel runeMonkRecorderPanel;

	private EventSubscribers eventSubscribers;

	private boolean recording = false;

	@Override
	protected void startUp() throws Exception
	{
		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "logo.png");

		runeMonkRecorderPanel = injector.getInstance(RuneMonkRecorderPanel.class);
		navButton = NavigationButton.builder()
				.tooltip("Runemonk Recorder")
				.icon(icon)
				.priority(5)
				.panel(runeMonkRecorderPanel)
				.build();

		clientToolbar.addNavigation(navButton);

		eventSubscribers = new EventSubscribers(client, new FileWriter(client));
	}

	public void startRecording() {
		if(recording)
			return;
		recording = true;

		FileWriter output = (FileWriter) eventSubscribers.getOutput();

		MetaInfo metaInfo = new MetaInfo();
		metaInfo.setUsername(client.getLocalPlayer().getName());
		metaInfo.setWorld(client.getWorld());
		metaInfo.setStartTime(System.currentTimeMillis());

		output.start(metaInfo);

		eventBus.register(eventSubscribers);
	}

	public void stopRecording() {
		if(!recording)
			return;
		recording = false;

		log.info("Stop pressed");
		FileWriter output = (FileWriter) eventSubscribers.getOutput();
		output.stop();
		eventSubscribers.finish();
		eventBus.unregister(eventSubscribers);
	}

	@Override
	protected void shutDown() throws Exception
	{
		stopRecording();
		clientToolbar.removeNavigation(navButton);
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
