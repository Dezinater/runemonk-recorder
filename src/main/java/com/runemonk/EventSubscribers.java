package com.runemonk;

import com.google.gson.JsonObject;
import com.runemonk.output.WriterBase;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Tile;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GroundObjectChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.client.eventbus.Subscribe;

public class EventSubscribers
{
	private final Client client;
	private final RuneMonkRecorderPanel panel;

	private final WriterBase output;
	private final DifferenceManager diff;

	private int ticksCount = 0;

	//overhead text needs an event subscriber since multiple messages can be sent per tick?

	//needs events for create and destroy actor to know when to make them invisible
	public EventSubscribers(Client client, RuneMonkRecorderPanel panel, WriterBase output)
	{
		this.client = client;
		this.output = output;
		this.panel = panel;
		this.diff = new DifferenceManager();
	}

	public WriterBase getOutput()
	{
		return output;
	}

	//idk if theres any better way to do this
	//maybe if onActorMove is added this can be updated
	@Subscribe
	public void onGameTick(GameTick tick)
	{
		//actors cannot be event based since theres no on move?
		//maybe all moving types cannot be event based?
		//objects and spotanims only need event based handlers since they dont change much and dont move
		client.getPlayers().forEach(this::actorDelegator);
		client.getNpcs().forEach(this::actorDelegator);
		//this one should be event based maybe
		client.getProjectiles().forEach(diff::projectileChanged);
		panel.setTicksCount(ticksCount++);
	}

	public void start()
	{
		ticksCount = 0;
		panel.setTicksCount(0);

		Tile[][][] tiles = client.getScene().getTiles();
		int plane = client.getScene().getMinLevel();
		for (int x = 0; x < tiles[plane].length; x++)
		{
			for (int y = 0; y < tiles[plane][x].length; y++)
			{
				if (tiles[plane][x][y].getWallObject() != null)
				{
					WallObjectSpawned wallObjectSpawned = new WallObjectSpawned();
					wallObjectSpawned.setWallObject(tiles[plane][x][y].getWallObject());
					onWallObjectSpawned(wallObjectSpawned);
				}

				if (tiles[plane][x][y].getDecorativeObject() != null)
				{
					DecorativeObjectSpawned decoObjectSpawned = new DecorativeObjectSpawned();
					decoObjectSpawned.setDecorativeObject(tiles[plane][x][y].getDecorativeObject());
					onDecorativeObjectSpawned(decoObjectSpawned);
				}

				if (tiles[plane][x][y].getGameObjects() != null)
				{
					for (GameObject gameObj : tiles[plane][x][y].getGameObjects())
					{
						if (gameObj != null)
						{
							GameObjectSpawned gameObjectSpawned = new GameObjectSpawned();
							gameObjectSpawned.setGameObject(gameObj);
							onGameObjectSpawned(gameObjectSpawned);
						}
					}

				}

				if (tiles[plane][x][y].getGroundObject() != null)
				{
					GroundObjectSpawned groundObjectSpawned = new GroundObjectSpawned();
					groundObjectSpawned.setGroundObject(tiles[plane][x][y].getGroundObject());
					onGroundObjectSpawned(groundObjectSpawned);
				}
			}
		}
	}

	public void finish()
	{
		diff.finish();
	}

	private void actorDelegator(Actor a)
	{
		if (a.getName() != null)
		{
			boolean isPlayer = a instanceof Player;
			boolean isNPC = a instanceof NPC;
			JsonObject json;

			if (isPlayer)
				json = diff.playerChanged((Player) a);
			else if (isNPC)
				json = diff.npcChanged((NPC) a);
			else
				json = diff.actorChanged(a);

			if (json.size() != 0)
			{
				output.write(a.hashCode(), json);
			}
		}
	}

	private void spawnEvent(Object obj)
	{
		JsonObject json = new JsonObject();
		json.addProperty("rsevent", "spawn");
		output.write(obj.hashCode(), json);
	}

	private void despawnEvent(Object obj)
	{
		JsonObject json = new JsonObject();
		json.addProperty("rsevent", "despawn");
		output.write(obj.hashCode(), json);
	}

	private void output(int hash, JsonObject json)
	{
		if (json.size() != 0)
		{
			output.write(hash, json);
		}
	}

	//DECORATIVE OBJECTS
	@Subscribe
	public void onDecorativeObjectSpawned(DecorativeObjectSpawned decoObj)
	{
		DecorativeObjectChanged decoObjectChanged = new DecorativeObjectChanged();
		decoObjectChanged.setDecorativeObject(decoObj.getDecorativeObject());
		onDecorativeObjectChanged(decoObjectChanged);

		spawnEvent(decoObj);
	}

	@Subscribe
	public void onDecorativeObjectDespawned(DecorativeObjectDespawned decoObj)
	{
		despawnEvent(decoObj);
	}

	@Subscribe
	public void onDecorativeObjectChanged(DecorativeObjectChanged decoObj)
	{
		if (decoObj.getDecorativeObject() != null)
			output(decoObj.hashCode(), diff.decorativeObjectChanged(decoObj.getDecorativeObject()));
	}


	//WALL OBJECTS
	@Subscribe
	public void onWallObjectSpawned(WallObjectSpawned wallObj)
	{
		WallObjectChanged wallObjectChanged = new WallObjectChanged();
		wallObjectChanged.setWallObject(wallObj.getWallObject());
		onWallObjectChanged(wallObjectChanged);

		spawnEvent(wallObj);
	}

	@Subscribe
	public void onWallObjectDespawned(WallObjectDespawned wallObj)
	{
		despawnEvent(wallObj);
	}

	@Subscribe
	public void onWallObjectChanged(WallObjectChanged wallObj)
	{
		if (wallObj.getWallObject() != null)
			output(wallObj.hashCode(), diff.wallObjectChanged(wallObj.getWallObject()));
	}

	//GAME OBJECTS
	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned gameObj)
	{
		GameObjectChanged gameObjectChanged = new GameObjectChanged();
		gameObjectChanged.setGameObject(gameObj.getGameObject());
		onGameObjectChanged(gameObjectChanged);

		spawnEvent(gameObj);
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned gameObj)
	{
		despawnEvent(gameObj);
	}

	@Subscribe
	public void onGameObjectChanged(GameObjectChanged gameObj)
	{
		if (gameObj.getGameObject() != null)
			output(gameObj.hashCode(), diff.gameObjectChanged(gameObj.getGameObject()));
	}

	//GROUND OBJECTS
	@Subscribe
	public void onGroundObjectSpawned(GroundObjectSpawned groundObj)
	{
		GroundObjectChanged groundObjectChanged = new GroundObjectChanged();
		groundObjectChanged.setGroundObject(groundObj.getGroundObject());
		onGroundObjectChanged(groundObjectChanged);

		spawnEvent(groundObj);
	}

	@Subscribe
	public void onGroundObjectDespawned(GroundObjectDespawned groundObj)
	{
		despawnEvent(groundObj);
	}

	@Subscribe
	public void onGroundObjectChanged(GroundObjectChanged groundObj)
	{
		if (groundObj.getGroundObject() != null)
			output(groundObj.hashCode(), diff.groundObjectChanged(groundObj.getGroundObject()));
	}
}
