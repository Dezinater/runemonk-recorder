package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;

public class TileObjectData extends Data
{
	String type = "";
	int id;
	WorldPoint worldLocation = new WorldPoint(-1, -1, -999);

	public TileObjectData()
	{
	}

	public TileObjectData(Object obj)
	{
		super(obj);
		setData(obj);
	}

	@Override
	public void setData(Object obj)
	{
		super.setData(obj);

		TileObject tileObj = (TileObject) obj;
		id = tileObj.getId();
		worldLocation = tileObj.getWorldLocation();
		type = "tileObj";
	}

	@Override
	public JsonObject getDifference(Data obj)
	{
		TileObjectData tileObj = (TileObjectData) obj;

		JsonObject differences = new JsonObject();

		if (id != tileObj.id)
			differences.addProperty("id", tileObj.id);

		if (type.compareTo(tileObj.type) != 0)
			differences.addProperty("type", tileObj.type);

		if (worldLocation != null && tileObj.worldLocation != null)
		{
			if (worldLocation.getX() != tileObj.worldLocation.getX())
				differences.addProperty("worldX", tileObj.worldLocation.getX());

			if (worldLocation.getY() != tileObj.worldLocation.getY())
				differences.addProperty("worldY", tileObj.worldLocation.getY());

			if (worldLocation.getPlane() != tileObj.worldLocation.getPlane())
				differences.addProperty("worldPlane", tileObj.worldLocation.getPlane());
		}

		return differences;
	}
}
