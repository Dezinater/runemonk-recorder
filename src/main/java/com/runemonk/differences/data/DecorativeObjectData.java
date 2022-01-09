package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.DecorativeObject;

public class DecorativeObjectData extends TileObjectData
{
	int xOffset, yOffset, config;

	public DecorativeObjectData()
	{
	}

	public DecorativeObjectData(Object obj)
	{
		super(obj);
		setData(obj);
	}

	@Override
	public void setData(Object obj)
	{
		super.setData(obj);

		DecorativeObject decoObj = (DecorativeObject) obj;
		xOffset = decoObj.getXOffset();
		yOffset = decoObj.getYOffset();
		config = decoObj.getConfig();
		type = "decoObj";
	}

	@Override
	public JsonObject getDifference(Data obj)
	{
		DecorativeObjectData decoObj = (DecorativeObjectData) obj;

		JsonObject differences = super.getDifference(obj);

		if (xOffset != decoObj.xOffset)
			differences.addProperty("xOffset", decoObj.xOffset);

		if (yOffset != decoObj.yOffset)
			differences.addProperty("yOffset", decoObj.yOffset);

		if (config != decoObj.config)
			differences.addProperty("config", decoObj.config);

		return differences;
	}
}
