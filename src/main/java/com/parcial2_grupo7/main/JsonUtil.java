package com.parcial2_grupo7.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

/**
 * Created by Leonardo on 23/07/2016.
 */

public class JsonUtil {

    public static String toJson(Object object) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(object);

    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
