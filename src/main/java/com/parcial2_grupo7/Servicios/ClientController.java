package com.parcial2_grupo7.Servicios;

import com.parcial2_grupo7.Clases.Post;
import com.parcial2_grupo7.Clases.Usuario;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

import static com.parcial2_grupo7.main.JsonUtil.json;
import static spark.Spark.*;
/**
 * Created by marti on 24/7/2016.
 */
public class ClientController {
    public ClientController(final ClientService clientService) {

        get("/webservices/usuarios", (req, res) -> clientService.getAllUsers(), json());

        get("/webservices/posts", (req, res) -> clientService.getAllPost(), json());

        get("/webservices/posts/:username", (req, res) -> clientService.getPostsByUser(req.params("username")), json());

        get("/webservices/createpost/:username/:cuerpo/:imagen", (req, resp) -> {

            return clientService.setPost(req.params("username"), req.params("imagen"), req.params("cuerpo"));
        }, json());

    }
}

