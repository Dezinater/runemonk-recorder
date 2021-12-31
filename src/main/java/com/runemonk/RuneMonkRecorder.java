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
import net.runelite.client.RuneLiteProperties;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.http.api.RuneLiteAPI;

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
		//metaInfo.setSourceVersion(RuneLiteProperties.getVersion());
		metaInfo.setSourceVersion(client.getRevision()+"");
		metaInfo.setUsername(config.includeUsername() ? client.getLocalPlayer().getName() : "");
		metaInfo.setWorld(client.getWorld());
		metaInfo.setStartTime(System.currentTimeMillis());

		output.start(metaInfo);

		eventSubscribers.start();
		eventBus.register(eventSubscribers);
	}

	public void stopRecording() {
		if(!recording)
			return;
		recording = false;

		log.info("Stop pressed");
		FileWriter output = (FileWriter) eventSubscribers.getOutput();
		output.setPrettyPrint(config.prettyPrint());
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

	@Provides
	RuneMonkRecorderConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RuneMonkRecorderConfig.class);
	}
}
