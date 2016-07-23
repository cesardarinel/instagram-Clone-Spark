package com.parcial2_grupo7.main;
import com.google.gson.Gson;
import com.parcial2_grupo7.Clases.Etiqueta;
import com.parcial2_grupo7.Clases.Post;
import com.parcial2_grupo7.Clases.Usuario;
import com.parcial2_grupo7.Servicios.MantenimientoPost;
import com.parcial2_grupo7.Servicios.MantenimientoUsuario;
import spark.ModelAndView;
import spark.Spark;
import com.parcial2_grupo7.main.JsonUtil;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

import static com.parcial2_grupo7.main.JsonUtil.json;
import static com.parcial2_grupo7.main.Main.creacionEtiquetas;
import static spark.Spark.*;

/**
 * Created by Leonardo on 23/07/2016.
 */


public class webservice {
    public webservice() {
    }

    public void start() {

        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        externalStaticFileLocation("upload");


        get("/webapi/timeline/:nombre", (request, response) -> {

            Usuario usuario = MantenimientoUsuario.getInstancia().find(request.params(":nombre"));
            ArrayList<Post> listaPost = new ArrayList<Post>(usuario.getPosts());

            return listaPost;
        }, json());


        post("/crearpost/:usuario/:imagen/:contenido/", (request, response) -> {

                    Post nu = new Post();
                    Usuario user = MantenimientoUsuario.getInstancia().find(request.params(":usuario"));
                    nu.setUsuario(user);
                    nu.setCuerpo(request.params("contenido"));
                    nu.setFecha(LocalDate.now());
                    nu.setImagen(request.params("imagen"));

                    MantenimientoPost.getInstancia().crear(nu);


            return "File uploaded";
        });
        }

    }
