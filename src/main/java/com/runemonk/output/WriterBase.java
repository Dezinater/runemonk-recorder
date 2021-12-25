package com.runemonk.output;

import com.google.gson.JsonObject;
import net.runelite.api.Client;

class Event {
    int identifier;
    JsonObject eventData;

    public Event(int identifier, JsonObject eventData){
        this.identifier = identifier;
        this.eventData = eventData;
    }
}

public abstract class WriterBase {

    protected Client client;

    public WriterBase(Client client) {
        this.client = client;
    }

    public void write(int hash, JsonObject event) {

    }

}