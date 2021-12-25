package com.runemonk;

import com.google.gson.JsonObject;
import com.runemonk.output.WriterBase;
import lombok.extern.slf4j.*;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.PlayerChanged;
import net.runelite.api.kit.KitType;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

@Slf4j
public class EventSubscribers {

    private Client client;

    private WriterBase output;
    private DifferenceManager diff;

    public EventSubscribers(Client client, WriterBase output) {
        this.client = client;
        this.output = output;
        this.diff = new DifferenceManager();
    }

    private void actorDelegator(Actor a) {
        if (a.getName() != null) {
            boolean isPlayer = a instanceof Player;
            boolean isNPC = a instanceof NPC;
            JsonObject json;

            if (isPlayer)
                json = diff.playerChanged((Player) a);
            else
                json = diff.actorChanged(a);

            if(json.size() != 0) {
                output.write(a.hashCode(), json);
                log.info(a.getName() + " " + json.toString());
            }
        }
    }

    //idk if theres any better way to do this
    //maybe if onActorMove is added this can be updated
    @Subscribe
    public void onGameTick(GameTick tick) {
        client.getPlayers().forEach(this::actorDelegator);
        client.getNpcs().forEach(this::actorDelegator);
    }

}
