package com.parcial2_grupo7.main;
import static spark.Spark.*;

/**
 * Created by cesar on 22/06/16.
 */
public class Main {
    // copiado del base de la pagina funciona genial 
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
