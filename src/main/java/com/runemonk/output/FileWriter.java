package com.runemonk.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;


//this should be rawWriter later on
//a new writer can be made so that the data is optizimized before output
//ex: calculate the bounds of how far an npc can wander around and replace npcs wandering movement data with that
//output can also be gzip after
@Slf4j
public class FileWriter extends WriterBase
{

	boolean prettyPrint;

	private ConcurrentSkipListMap<Integer, ArrayList<Event>> ticks = new ConcurrentSkipListMap<>();

	private PrintWriter printWriter;

	private String folder = System.getProperty("user.home") + "/Videos";

	public FileWriter(Client client)
	{
		super(client);
	}

	@Override
	public void start(MetaInfo info) throws FileNotFoundException
	{
		super.start(info);
		printWriter = new PrintWriter(folder + "/" + info.getStartTime() + ".rsrec");
	}

	public void stop()
	{
		writeToFile();
		ticks = new ConcurrentSkipListMap<>();
	}

	public void setFolder(String folder)
	{
		if (folder != null && !folder.equals(""))
			this.folder = folder;
	}

	public void setPrettyPrint(boolean prettyPrint)
	{
		this.prettyPrint = prettyPrint;
	}

	private void writeToFile()
	{
		JsonObject finalJson = new JsonObject();
		Gson gson = new Gson();
		finalJson.add("metaInfo", gson.toJsonTree(metaInfo));

		synchronized (ticks)
		{
			//go through all of the recorded ticks
			for (Map.Entry<Integer, ArrayList<Event>> entry : ticks.entrySet())
			{
				Integer key = entry.getKey();
				ArrayList<Event> value = entry.getValue();

				JsonArray tickJson = new JsonArray();

				//all of the events that happened on this tick
				for (Event event : value)
				{
					tickJson.add(gson.toJsonTree(event));
				}
				finalJson.add(key + "", tickJson);
			}
		}

		String outputString;
		if (prettyPrint)
		{
			Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			outputString = gsonBuilder.toJson(finalJson);
		}
		else
		{
			outputString = finalJson.toString();
		}

		printWriter.write(outputString);
		printWriter.flush();
		printWriter.close();
	}

	@Override
	public void write(int hash, JsonObject event)
	{
		if (!ticks.containsKey(client.getTickCount()))
			ticks.put(client.getTickCount(), new ArrayList<Event>());

		ArrayList<Event> jsonList = ticks.get(client.getTickCount());
		jsonList.add(new Event(hash, event));
	}
}
