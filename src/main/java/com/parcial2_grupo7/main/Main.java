package com.parcial2_grupo7.main;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by cesar on 22/06/16.
 */
public class Main {
    // copiado del base de la pagina funciona genial 
    public static void main(String[] args) {

        Spark.staticFileLocation("/Recursos");
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/template");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            //TODO modificar plantilla home/index
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            //TODO crear plantilla login
            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);

        get("/register", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            //TODO crear plantilla registrar
            return new ModelAndView(attributes, "signup.ftl");
        }, freeMarkerEngine);

    }
}
