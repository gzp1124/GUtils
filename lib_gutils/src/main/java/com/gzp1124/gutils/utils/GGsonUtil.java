package com.gzp1124.gutils.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GGsonUtil {
    public static <T> T json2Bean(String json, Class<T> clazz) {
        Gson jGson = new Gson();
        try {
            T t = jGson.fromJson(json, clazz);
            return t;
        } catch (JsonParseException e) {
            errorJson = true;
            return null;
        }
    }

    public static boolean errorJson = false;
}
