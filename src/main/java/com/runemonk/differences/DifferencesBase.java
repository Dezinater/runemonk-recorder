package com.runemonk.differences;

import com.google.gson.JsonObject;
import com.runemonk.differences.data.Data;
import net.runelite.api.Node;
import lombok.extern.slf4j.*;
import sun.util.logging.resources.logging;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Slf4j

public class DifferencesBase<T extends Data> {

    HashMap<Integer, T> entities;

    Class<T> clazz;

    public DifferencesBase(Class<T> clazz) {
        this.clazz = clazz;
        setup();
    }

    public void setup() {
        entities = new HashMap<>();
    }

    protected T getPreviousState(int hash) {
        return entities.get(hash);
    }

    public JsonObject getDifference(Object currentState) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T previousStateData = getPreviousState(currentState.hashCode());

        //if theres no previous state then make an empty one
        if(previousStateData == null) {
            previousStateData = clazz.getDeclaredConstructor().newInstance();
            entities.put(currentState.hashCode(), previousStateData);
        }

        //create a data class and set the values
        T currentStateData = clazz.getDeclaredConstructor().newInstance();
        currentStateData.setData(currentState);

        //compare the two data classes
        JsonObject difference = previousStateData.getDifference(currentStateData);

        //update the previous values to the current ones
        previousStateData.setData(currentState);

        return difference;
    }

}
