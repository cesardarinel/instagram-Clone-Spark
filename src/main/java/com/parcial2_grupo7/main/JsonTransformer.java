package com.parcial2_grupo7.main;

/**
 * Created by Leonardo on 23/07/2016.
 */


import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
