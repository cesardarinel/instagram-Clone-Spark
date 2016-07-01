package com.parcial2_grupo7.main;
import com.parcial2_grupo7.Clases.Usuario;
import com.parcial2_grupo7.main.Main;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.halt;

//creado sin computador, txt plain, validar 
public class Filtro {
// metodo que aplica los filtros

    public void aplicarFiltros(){


//valido los lugares donde solo los usuario registrado pueden entrar 
        before("/home",(request, response) -> {
            Usuario usuario=request.session().attribute("usuario");
            if(usuario == null ){

                response.redirect("/");
            }
        });
           before("/crearpost",(request, response) -> {
            Usuario usuario=request.session().attribute("usuario");
            if(usuario == null ){

                response.redirect("/");
            }
        });

    }
}
