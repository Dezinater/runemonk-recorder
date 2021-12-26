package com.runemonk.differences.data;

import com.google.gson.JsonObject;
import net.runelite.api.Actor;
import net.runelite.api.coords.WorldPoint;
import lombok.extern.slf4j.*;

@Slf4j
public class ActorData extends Data {

    int orientation, currentOrientation, graphic, healthRatio, healthScale;
    int animation, animationFrame, idlePoseAnimation, idleRotateLeft, idleRotateRight;
    int poseAnimation, runAnimation, spotAnimFrame, walkAnimation, walkRotate180, walkRotateLeft, walkRotateRight;

    String overheadText;
    long interacting;
    WorldPoint worldLocation = new WorldPoint(-1, -1, -999);

    public ActorData() {

    }

    public ActorData(Object obj) {
        super(obj);
        setData(obj);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);

        Actor a = (Actor) obj;
        orientation = a.getOrientation();
        currentOrientation = a.getCurrentOrientation();
        graphic = a.getGraphic();
        healthRatio = a.getHealthRatio();
        healthScale = a.getHealthScale();
        animation = a.getAnimation();
        animationFrame = a.getAnimationFrame();
        idlePoseAnimation = a.getIdlePoseAnimation();
        idleRotateLeft = a.getIdleRotateLeft();
        idleRotateRight = a.getIdleRotateRight();
        poseAnimation = a.getPoseAnimation();
        runAnimation = a.getRunAnimation();
        spotAnimFrame = a.getSpotAnimFrame();
        walkAnimation = a.getWalkAnimation();
        walkRotate180 = a.getWalkRotate180();
        walkRotateLeft = a.getWalkRotateLeft();
        walkRotateRight = a.getWalkRotateRight();

        overheadText = a.getOverheadText();

        if(a.getInteracting() != null)
            interacting = a.getInteracting().getHash();
        else
            interacting = -1;

        if(a.getName() != null)
            worldLocation = a.getWorldLocation();

    }

    //reflection would make this alot cleaner but fuck it
    @Override
    public JsonObject getDifference(Data obj) {
        ActorData a = (ActorData) obj;

        JsonObject differences = new JsonObject();

        if (orientation != a.orientation)
            differences.addProperty("orientation", a.orientation);

        if (currentOrientation != a.currentOrientation)
            differences.addProperty("currentOrientation", a.currentOrientation);

        if (graphic != a.graphic)
            differences.addProperty("graphic", a.graphic);

        if (healthRatio != a.healthRatio)
            differences.addProperty("healthRatio", a.healthRatio);

        if (healthScale != a.healthScale)
            differences.addProperty("healthScale", a.healthScale);

        if (animation != a.animation)
            differences.addProperty("animation", a.animation);

        if (idlePoseAnimation != a.idlePoseAnimation)
            differences.addProperty("idlePoseAnimation", a.idlePoseAnimation);

        if (idleRotateLeft != a.idleRotateLeft)
            differences.addProperty("idleRotateLeft", a.idleRotateLeft);

        if (idleRotateRight != a.idleRotateRight)
            differences.addProperty("idleRotateRight", a.idleRotateRight);

        if (poseAnimation != a.poseAnimation)
            differences.addProperty("poseAnimation", a.poseAnimation);

        if (runAnimation != a.runAnimation)
            differences.addProperty("runAnimation", a.runAnimation);

        if (spotAnimFrame != a.spotAnimFrame)
            differences.addProperty("spotAnimFrame", a.spotAnimFrame);

        if (walkAnimation != a.walkAnimation)
            differences.addProperty("walkAnimation", a.walkAnimation);

        if (spotAnimFrame != a.spotAnimFrame)
            differences.addProperty("spotAnimFrame", a.spotAnimFrame);

        if (walkRotate180 != a.walkRotate180)
            differences.addProperty("walkRotate180", a.walkRotate180);

        if (walkRotateLeft != a.walkRotateLeft)
            differences.addProperty("walkRotateLeft", a.walkRotateLeft);

        if (walkRotateRight != a.walkRotateRight)
            differences.addProperty("walkRotateRight", a.walkRotateRight);

        if (overheadText != a.overheadText)
            differences.addProperty("overheadText", a.overheadText);

        if (interacting != a.interacting)
            differences.addProperty("walkRotateLeft", a.interacting);

        if(worldLocation != null && a.worldLocation != null) {
            if (worldLocation.getX() != a.worldLocation.getX())
                differences.addProperty("worldX", a.worldLocation.getX());

            if (worldLocation.getY() != a.worldLocation.getY())
                differences.addProperty("worldY", a.worldLocation.getY());

            if (worldLocation.getPlane() != a.worldLocation.getPlane())
                differences.addProperty("worldPlane", a.worldLocation.getPlane());
        }

        return differences;
    }

}
