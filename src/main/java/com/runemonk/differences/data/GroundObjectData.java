package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.GroundObject;

public class GroundObjectData extends TileObjectData
{
	int config;

	public GroundObjectData()
	{
	}

	public GroundObjectData(Object obj)
	{
		super(obj);
		setData(obj);
	}

	@Override
	public void setData(Object obj)
	{
		super.setData(obj);

		GroundObject groundObj = (GroundObject) obj;
		config = groundObj.getConfig();
		type = "groundObj";
	}

	@Override
	public JsonObject getDifference(Data obj)
	{
		GroundObjectData groundObj = (GroundObjectData) obj;
		JsonObject differences = super.getDifference(obj);

		if (config != groundObj.config)
			differences.addProperty("config", groundObj.config);

		return differences;
	}
}
