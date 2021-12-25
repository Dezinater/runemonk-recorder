package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.NPC;
import lombok.extern.slf4j.*;

@Slf4j
public class NpcData extends Data {

    int npcId;

    public NpcData() { }

    public NpcData(Object obj) {
        super(obj);
        setData(obj);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);

        NPC n = (NPC) obj;
        npcId = n.getId();
    }

    @Override
    public JsonObject getDifference(Data obj) {
        NpcData n = (NpcData) obj;

        JsonObject differences = new JsonObject();

        if (npcId != n.npcId)
            differences.addProperty("npcId", n.npcId);

        return differences;
    }

}
