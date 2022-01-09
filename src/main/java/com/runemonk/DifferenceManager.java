package com.runemonk;

import com.google.gson.JsonObject;
import com.runemonk.differences.DifferencesBase;
import com.runemonk.differences.data.ActorData;
import com.runemonk.differences.data.DecorativeObjectData;
import com.runemonk.differences.data.GameObjectData;
import com.runemonk.differences.data.GroundObjectData;
import com.runemonk.differences.data.NpcData;
import com.runemonk.differences.data.PlayerData;
import com.runemonk.differences.data.ProjectileData;
import com.runemonk.differences.data.WallObjectData;
import net.runelite.api.Actor;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GroundObject;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Projectile;
import net.runelite.api.WallObject;

public class DifferenceManager
{
	DifferencesBase actorDiff, playerDiff, npcDiff, projectileDiff, decorativeObjectDiff, wallObjectDiff, gameObjectDiff, groundObjectDiff;

	public DifferenceManager()
	{
		actorDiff = new DifferencesBase<>(ActorData.class);
		playerDiff = new DifferencesBase<>(PlayerData.class);
		npcDiff = new DifferencesBase<>(NpcData.class);
		projectileDiff = new DifferencesBase<>(ProjectileData.class);
		decorativeObjectDiff = new DifferencesBase<>(DecorativeObjectData.class);
		wallObjectDiff = new DifferencesBase<>(WallObjectData.class);
		gameObjectDiff = new DifferencesBase<>(GameObjectData.class);
		groundObjectDiff = new DifferencesBase<>(GroundObjectData.class);
	}

	public void finish()
	{
		//setup to reset them back to starting state
		actorDiff.setup();
		playerDiff.setup();
		npcDiff.setup();
		projectileDiff.setup();
		decorativeObjectDiff.setup();
		wallObjectDiff.setup();
		gameObjectDiff.setup();
		groundObjectDiff.setup();
	}

	//changed methods can still be called even if theres no changes we are tracking
	//probably avoid returning null jsonobjects or dont insert them into the final output
	public JsonObject actorChanged(Actor a)
	{
		JsonObject json = null;

		try
		{
			json = actorDiff.getDifference(a);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public JsonObject playerChanged(Player p)
	{
		JsonObject json = null;

		try
		{
			json = playerDiff.getDifference(p.getName().hashCode(), p);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public JsonObject npcChanged(NPC n)
	{
		JsonObject json = null;

		try
		{
			json = npcDiff.getDifference(n);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public JsonObject projectileChanged(Projectile p)
	{
		JsonObject json = null;

		try
		{
			json = projectileDiff.getDifference(p);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public JsonObject decorativeObjectChanged(DecorativeObject decoObj)
	{
		JsonObject json = null;

		try
		{
			json = decorativeObjectDiff.getDifference(decoObj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public JsonObject wallObjectChanged(WallObject wallObj)
	{
		JsonObject json = null;

		try
		{
			json = wallObjectDiff.getDifference(wallObj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public JsonObject gameObjectChanged(GameObject gameObj)
	{
		JsonObject json = null;

		try
		{
			json = gameObjectDiff.getDifference(gameObj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

	public JsonObject groundObjectChanged(GroundObject groundObj)
	{
		JsonObject json = null;

		try
		{
			json = groundObjectDiff.getDifference(groundObj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return json;
	}

}
