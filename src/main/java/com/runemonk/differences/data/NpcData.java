package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.NPC;

public class NpcData extends ActorData
{
	int npcId;

	public NpcData()
	{
	}

	public NpcData(Object obj)
	{
		super(obj);
		setData(obj);
	}

	@Override
	public void setData(Object obj)
	{
		super.setData(obj);

		NPC n = (NPC) obj;
		npcId = n.getId();
	}

	@Override
	public JsonObject getDifference(Data obj)
	{
		NpcData n = (NpcData) obj;

		JsonObject differences = super.getDifference(obj);

		if (npcId != n.npcId)
			differences.addProperty("npcId", n.npcId);

		return differences;
	}

}
