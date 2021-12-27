package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;

public class GameObjectData extends TileObjectData {

    int orientation, modelOrientation, sizeX, sizeY;

    public GameObjectData() { }

    public GameObjectData(Object obj) {
        super(obj);
        setData(obj);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);

        GameObject n = (GameObject) obj;
        orientation = n.getOrientation().getAngle();
        modelOrientation = n.getModelOrientation();
        sizeX = n.sizeX();
        sizeY = n.sizeY();
        worldLocation = n.getWorldLocation();
        type = "gameObj";
    }

    @Override
    public JsonObject getDifference(Data obj) {
        GameObjectData gameObj = (GameObjectData) obj;

        JsonObject differences = super.getDifference(obj);

        if (id != gameObj.id)
            differences.addProperty("id", gameObj.id);

        if (orientation != gameObj.orientation)
            differences.addProperty("orientation", gameObj.orientation);

        if (modelOrientation != gameObj.modelOrientation)
            differences.addProperty("modelOrientation", gameObj.modelOrientation);

        if (sizeX != gameObj.sizeX)
            differences.addProperty("sizeX", gameObj.sizeX);

        if (sizeY != gameObj.sizeY)
            differences.addProperty("sizeY", gameObj.sizeY);

        return differences;
    }
}
