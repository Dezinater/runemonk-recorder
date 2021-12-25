package com.runemonk.output;

import com.google.gson.JsonObject;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;

public class FileWriter extends WriterBase {

    private HashMap<Integer, ArrayList> ticks = new HashMap<>();

    public FileWriter(Client client) {
        super(client);
    }

    @Override
    public void write(int hash, JsonObject event) {
        if(!ticks.containsKey(client.getTickCount()))
            ticks.put(client.getTickCount(), new ArrayList<Event>());

        ArrayList<Event> jsonList = ticks.get(client.getTickCount());
        jsonList.add(new Event(hash, event));
    }
}
