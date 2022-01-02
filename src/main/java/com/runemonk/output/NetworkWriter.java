package com.runemonk.output;

import com.google.gson.JsonObject;
import net.runelite.api.Client;

public class NetworkWriter extends WriterBase
{
	public NetworkWriter(Client client)
	{
		super(client);
	}

	@Override
	public void write(int hash, JsonObject event)
	{

	}
}
