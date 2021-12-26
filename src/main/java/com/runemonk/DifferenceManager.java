package com.runemonk;

import com.google.gson.JsonObject;
import com.runemonk.differences.DifferencesBase;
import com.runemonk.differences.data.ActorData;
import com.runemonk.differences.data.NpcData;
import com.runemonk.differences.data.PlayerData;
import net.runelite.api.Actor;
import net.runelite.api.NPC;
import net.runelite.api.Player;

public class DifferenceManager {

    DifferencesBase actorDifferences, playerDifferences, npcDifferences;

    public DifferenceManager() {
        actorDifferences = new DifferencesBase<>(ActorData.class);
        playerDifferences = new DifferencesBase<>(PlayerData.class);
        npcDifferences = new DifferencesBase<>(NpcData.class);
    }

    public void finish() {
        actorDifferences.setup();
        playerDifferences.setup();
        npcDifferences.setup();
    }

    //changed methods can still be called even if theres no changes we are tracking
    //probably avoid returning null jsonobjects or dont insert them into the final output
    public JsonObject actorChanged(Actor a) {
        JsonObject json = null;

        try {
            json = actorDifferences.getDifference(a);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public JsonObject playerChanged(Player p) {
        JsonObject json = null;

        try {
            json = playerDifferences.getDifference(p);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public JsonObject npcChanged(NPC n) {
        JsonObject json = null;

        try {
            json = npcDifferences.getDifference(n);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }
}
