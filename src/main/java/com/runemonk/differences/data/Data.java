package com.runemonk.differences.data;

import com.google.gson.JsonObject;

public class Data
{
	protected long hash;

	public Data()
	{
	}

	public Data(Object obj)
	{
	}

	public void setData(Object obj)
	{
		hash = obj.hashCode();
	}

	public JsonObject getDifference(Data obj)
	{
		return new JsonObject();
	}
}
