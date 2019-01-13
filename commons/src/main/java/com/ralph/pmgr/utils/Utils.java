package com.ralph.pmgr.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class Utils {

    public static Object toClass(Object object, Class type) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(object);
        Object p = gson.fromJson(jsonElement, type);
        return p;
    }

    public static void printJson(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        System.out.println(json);
    }
}
