package com.parcial2_grupo7.main;

import com.parcial2_grupo7.Clases.Etiqueta;
import com.parcial2_grupo7.Clases.Post;
import com.parcial2_grupo7.main.Filtro;
import com.parcial2_grupo7.Clases.Usuario;
import com.parcial2_grupo7.Servicios.BaseDatos;
import com.parcial2_grupo7.Servicios.MantenimientoEtiqueta;
import com.parcial2_grupo7.Servicios.MantenimientoPost;
import com.parcial2_grupo7.Servicios.MantenimientoUsuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import javax.persistence.NoResultException;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static spark.Spark.*;

/**
 * Created by cesar on 22/06/16.
 */
public class Main {

    public static void main(String[] args) {


        staticFileLocation("/Recursos");
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/template");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

       //iniciamos la base de datos
        try {
            BaseDatos.startDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Filtro ft = new Filtro();
        ft.aplicarFiltros();

    	 get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            //TODO modificar plantilla home/index
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);
        get("/home", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            //TODO modificar plantilla home/index
            return new ModelAndView(attributes, "timeline.ftl");
        }, freeMarkerEngine);
        /**
         * Login
         */
        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            if(request.queryParams("r") != null) {
                attributes.put("message", "Estaba registrado y puede iniciar sesión ahora");
            }
            if(request.queryParams("err") != null) {
                attributes.put("error", "Credenciales no validas...");
            }

            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);

        post("/login", (request, response) -> {
            Map<String, Object> map = new HashMap<>();
            Session session=request.session(true);
            Usuario usuario = MantenimientoUsuario.getInstancia().find(request.queryParams("username"));

            if (usuario==null ||!request.queryParams("password").equals(usuario.getPassword())){
                response.redirect("/login?err=1");
                halt();
            }
            else {
                session.attribute("usuario", usuario);
                response.redirect("/home");
                halt();
            }
            return null;
        });

        get("/cerrarsesion", (request, response) -> {
            request.session().invalidate();
            response.redirect("/");
            return "Cesion cerrada";
        });
        /**
         * registro
         */
        get("/register", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);

        post("/register", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String error = null;
            try {
                if (!request.queryParams("username").equals(null) ){/*|| request.queryParams("email") == null
                    || request.queryParams("password") == null|| request.queryParams("password2") == null){*/
                    Usuario usuario =new Usuario();
                    usuario.setPassword(request.queryParams("password"));
                    usuario.setUsername(request.queryParams("username"));
                    usuario.setEmail(request.queryParams("email"));
                    MantenimientoUsuario.getInstancia().crear(usuario);
                    response.redirect("/login?r=1");
                    halt();
                } else {
                    error = "Error guardando";
                }
            }catch(Exception  e) {
                error = "exception error";
            }

            attributes.put("error", error);
            attributes.put("username", request.queryParams("username"));
            attributes.put("email", request.queryParams("email"));
            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);

        get("/crearpost", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("post", new Post("", null, "", null, null,null));
            attributes.put("stringEtiquetas", "");

            return new ModelAndView(attributes, "crearPost.ftl");
        }, freeMarkerEngine);

        post("/crearpost", "multipart/form-data", (request, response) -> {

            //TODO Crear el post y guardalo en la base de datos
            //TODO cambiar direccion en donde se van a guardar las fotos
            //TODO Crear restrincion para que solo se pueda subir fotos

            //CODIGO PARA GUARDAR LA IMAGEN
            //- Servlet 3.x config
            String location = "src\\main\\resources\\img\\";  // the directory location where files will be stored
            long maxFileSize = 100000000;  // the maximum size allowed for uploaded files
            long maxRequestSize = 100000000;  // the maximum size allowed for multipart/form-data requests
            int fileSizeThreshold = 1024;  // the size threshold after which files will be written to disk
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold);
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

            /*
            Collection<Part> parts = request.raw().getParts();
            for(Part part : parts) {
                System.out.println("Filename:");
                System.out.println(part.getSubmittedFileName());
            }*/

            String fName = request.raw().getPart("upfile").getSubmittedFileName();
            System.out.println("File: "+fName);

            Part uploadedFile = request.raw().getPart("upfile");
            Path out = Paths.get(location+fName);

            try (final InputStream in = uploadedFile.getInputStream()) {
                Files.copy(in, out);
                uploadedFile.delete();
            }
            // cleanup
            multipartConfigElement = null;
           // parts = null;
            uploadedFile = null;

            String etiquetasStr = request.queryParams("etiquetas");
            String etiquetas[] = etiquetasStr.split("\\s*,\\s*");
            Usuario usuario = request.session().attribute("usuario");
            Post post =new Post();
            post.setUsuario(usuario);
            post.setCuerpo(request.queryParams("contenido"));
            post.setEtiquetas(creacionEtiquetas(etiquetas));
            post.setFecha(LocalDate.now());
            post.setImagen(fName);

            MantenimientoPost.getInstancia().crear(post);
            response.redirect("/login?r=1");
            halt();

            response.redirect("/home");
            return "";
        });


    }

    //se crean las etiquetas si es necesario y se entran en una lista para luego asignarles un post.
    public static List<Etiqueta> creacionEtiquetas (String[] etiquetas){
        List<Etiqueta> setEtiquetas= null;
        for (String etiqueta : etiquetas) {
            try{
                Etiqueta etiqueta1 = (Etiqueta) MantenimientoEtiqueta.getInstancia().getEntityManager().createQuery("SELECT E FROM Etiqueta E WHERE E.tag='" + etiqueta + "'").getSingleResult();
                setEtiquetas.add(etiqueta1);

            }catch (NoResultException e){
                System.out.println(etiqueta);
                Etiqueta newEtiqueta = new Etiqueta(0,etiqueta);
                MantenimientoEtiqueta.getInstancia().crear(newEtiqueta);
                setEtiquetas.add(newEtiqueta);// <---------------- aqui esta el error dice que es null, pero cuando lo imprimo arriba tiene un valor.
            }

        }
        return setEtiquetas;
    }
}
