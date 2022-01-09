package com.runemonk;

import com.google.inject.Provides;

import javax.inject.Inject;

import com.runemonk.output.FileWriter;
import com.runemonk.output.MetaInfo;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

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
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "panelIcon.png");

		runeMonkRecorderPanel = injector.getInstance(RuneMonkRecorderPanel.class);
		navButton = NavigationButton.builder()
				.tooltip("RuneMonk Recorder")
				.icon(icon)
				.priority(5)
				.panel(runeMonkRecorderPanel)
				.build();

		clientToolbar.addNavigation(navButton);
		eventSubscribers = new EventSubscribers(client, runeMonkRecorderPanel, new FileWriter(client));
	}

	public boolean isAbleToRecord()
	{
		return client.getGameState() == GameState.LOGGED_IN && !recording;
	}

	public void startRecording() throws FileNotFoundException
	{
		if (recording)
			return;


		FileWriter output = (FileWriter) eventSubscribers.getOutput();

		MetaInfo metaInfo = new MetaInfo();
		//metaInfo.setSourceVersion(RuneLiteProperties.getVersion());
		metaInfo.setSourceVersion(client.getRevision() + "");
		metaInfo.setUsername(config.includeUsername() ? client.getLocalPlayer().getName() : "");
		metaInfo.setWorld(client.getWorld());
		metaInfo.setStartTime(System.currentTimeMillis());

		output.setFolder(configManager.getConfiguration("runemonk", "rsrecfolder"));
		output.start(metaInfo);

		eventSubscribers.start();
		eventBus.register(eventSubscribers);

		recording = true;
	}

	public void stopRecording()
	{
		if (!recording)
			return;

		FileWriter output = (FileWriter) eventSubscribers.getOutput();
		output.setPrettyPrint(config.prettyPrint());
		output.stop();
		eventSubscribers.finish();
		eventBus.unregister(eventSubscribers);

		recording = false;
	}

	@Override
	protected void shutDown()
	{
		stopRecording();
		clientToolbar.removeNavigation(navButton);
	}

	@Provides
	RuneMonkRecorderConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RuneMonkRecorderConfig.class);
	}
}
