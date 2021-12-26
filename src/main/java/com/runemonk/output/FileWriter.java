package com.runemonk.output;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.runelite.api.Client;

import javax.inject.Inject;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//this should be rawWriter later on
//a new writer can be made so that the data is optizimized before output
//ex: calculate the bounds of how far an npc can wander around and replace npcs wandering movement data with that
//output can also be gzip after
public class FileWriter extends WriterBase {

    private TreeMap<Integer, ArrayList<Event>> ticks = new TreeMap<>();

    private PrintWriter printWriter;

    public FileWriter(Client client) {
        super(client);
    }

    @Override
    public void start(MetaInfo info) {
        super.start(info);
        try {
            printWriter = new PrintWriter(info.getStartTime() + ".json");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        writeToFile();
        ticks = new TreeMap<>();
    }

    private void writeToFile() {
        JsonObject finalOutput = new JsonObject();
        Gson gson = new Gson();
        finalOutput.add("metaInfo", gson.toJsonTree(metaInfo));

        //go through all of the recorded ticks
        for(Map.Entry<Integer, ArrayList<Event>> entry : ticks.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Event> value = entry.getValue();

            JsonArray tickJson = new JsonArray();

            //all of the events that happened on this tick
            for(Event event : value) {
                tickJson.add(gson.toJsonTree(event));
            }
            finalOutput.add(key+"", tickJson);
        }

        printWriter.write(finalOutput.toString());
        printWriter.flush();
        printWriter.close();
    }

    @Override
    public void write(int hash, JsonObject event) {
        if(!ticks.containsKey(client.getTickCount()))
            ticks.put(client.getTickCount(), new ArrayList<Event>());

        ArrayList<Event> jsonList = ticks.get(client.getTickCount());
        jsonList.add(new Event(hash, event));
    }
}
