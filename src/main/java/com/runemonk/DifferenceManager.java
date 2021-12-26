package com.runemonk;

import com.google.gson.JsonObject;
import com.runemonk.differences.DifferencesBase;
import com.runemonk.differences.data.ActorData;
import com.runemonk.differences.data.NpcData;
import com.runemonk.differences.data.PlayerData;
import com.runemonk.differences.data.ProjectileData;
import net.runelite.api.Actor;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Projectile;

public class DifferenceManager {

    DifferencesBase actorDiff, playerDiff, npcDiff, projectileDiff;

    public DifferenceManager() {
        actorDiff = new DifferencesBase<>(ActorData.class);
        playerDiff = new DifferencesBase<>(PlayerData.class);
        npcDiff = new DifferencesBase<>(NpcData.class);
        projectileDiff = new DifferencesBase<>(ProjectileData.class);
    }

    public void finish() {
        actorDiff.setup();
        playerDiff.setup();
        npcDiff.setup();
        projectileDiff.setup();
    }

    //changed methods can still be called even if theres no changes we are tracking
    //probably avoid returning null jsonobjects or dont insert them into the final output
    public JsonObject actorChanged(Actor a) {
        JsonObject json = null;

        try {
            json = actorDiff.getDifference(a);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public JsonObject playerChanged(Player p) {
        JsonObject json = null;

        try {
            json = playerDiff.getDifference(p.getName().hashCode(), p);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public JsonObject npcChanged(NPC n) {
        JsonObject json = null;

        try {
            json = npcDiff.getDifference(n);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public JsonObject projectileChanged(Projectile p) {
        JsonObject json = null;

        try {
            json = projectileDiff.getDifference(p);
        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }
}
