package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.NPC;
import net.runelite.api.WallObject;

public class WallObjectData extends TileObjectData {

    int config, orientationA, orientationB;

    public WallObjectData() { }

    public WallObjectData(Object obj) {
        super(obj);
        setData(obj);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);

        WallObject wallObject = (WallObject) obj;
        config = wallObject.getConfig();
        orientationA = wallObject.getOrientationA();
        orientationB = wallObject.getOrientationB();
        type = "wallObj";
    }

    @Override
    public JsonObject getDifference(Data obj) {
        WallObjectData wallObj = (WallObjectData) obj;
        JsonObject differences = super.getDifference(obj);

        if (config != wallObj.config)
            differences.addProperty("config", wallObj.config);

        if (orientationA != wallObj.orientationA)
            differences.addProperty("orientationA", wallObj.orientationA);

        if (orientationB != wallObj.orientationB)
            differences.addProperty("orientationB", wallObj.orientationB);

        return differences;
    }
}
