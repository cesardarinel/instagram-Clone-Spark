package com.parcial2_grupo7.main;

import com.parcial2_grupo7.Clases.Comentario;
import com.parcial2_grupo7.Clases.Etiqueta;
import com.parcial2_grupo7.Clases.Post;
import com.parcial2_grupo7.Servicios.*;
import com.parcial2_grupo7.Clases.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.NoResultException;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by cesar on 22/06/16.
 */
public class Main {

    public static void main(String[] args) {

        //todo etiquetar amigos en post a los usuarios que son amigos nuestros para luego publicar.
        //todo tipos de cuentas privadas/publicas
        //todo followers y follow
        //todo likes
        //todo  Asignar un “like” o agregar un comentario debe notificar al usuario dueño de la publicación de dicho evento por la plataforma.

        staticFileLocation("/Recursos");
        enableDebugScreen();
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/template");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        //variables para manejo de imagenes
        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        externalStaticFileLocation("upload");

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

            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);
        get("/home", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();


            Usuario usuario = request.session().attribute("usuario");
            System.out.println(usuario.getUsername());
            List<Post> listaPost = MantenimientoPost.getInstancia().findAll();
            Collections.reverse(listaPost);
            attributes.put("posts", listaPost);
            attributes.put("usuario", usuario);


            return new ModelAndView(attributes, "timelinevs2.ftl");
        }, freeMarkerEngine);

        get("/usuario/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario usuario = (Usuario) MantenimientoUsuario.getInstancia().getEntityManager().createQuery("SELECT U FROM Usuario U WHERE U.username='" + request.params("username") + "'").getSingleResult();
            System.out.println(usuario.getUsername());
            Usuario userInSesion = request.session().attribute("usuario");
            List<Post> listaPostUsuario = usuario.getPosts();
            System.out.println(listaPostUsuario.size());
            Collections.reverse(listaPostUsuario);
            attributes.put("posts", listaPostUsuario);
            attributes.put("usuario", usuario);
            attributes.put("usuarioEnSesion", userInSesion);


            return new ModelAndView(attributes, "usuario.ftl");
        }, freeMarkerEngine);

        //todo hacerlo tipo modal.
        get("/usuario/:username/:post_id", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario usuarioSesion = request.session().attribute("usuario");
            Post post = MantenimientoPost.getInstancia().find(Integer.parseInt(request.params("post_id")));
            //Todo Bug muestra 4 veces el mismo comentario
            System.out.println(post.getComentarios().size());
            attributes.put("post", post);
            attributes.put("usuarioEnSesion", usuarioSesion);


            return new ModelAndView(attributes, "vistaprevia.ftl");
        }, freeMarkerEngine);

        get("/editarcuenta", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario usuario = request.session().attribute("usuario");
            attributes.put("usuario", usuario);

            attributes.put("upfile", usuario.getImagen());
            attributes.put("descripcion", usuario.getDescripcion());
            attributes.put("email", usuario.getEmail());
            attributes.put("password", usuario.getPassword());
            attributes.put("password2", "");


            return new ModelAndView(attributes, "editarcuenta.ftl");
        }, freeMarkerEngine);

        post("/editarcuenta", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String error = null;
            Usuario usuario = request.session().attribute("usuario");
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream is = request.raw().getPart("upfile").getInputStream()) {
                // Use the input stream to create a file
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
                String mimetype = request.raw().getPart("upfile").getContentType();
                String type = mimetype.split("/")[0];
                if (type.equals("image")) {
                    if (request.queryParams("password").equals(request.queryParams("password2"))) {
                        usuario.setPassword(request.queryParams("password"));
                        System.out.println(request.raw().getPart("upfile").getInputStream().read());
                        if (request.raw().getPart("upfile").getInputStream().read() != -1) {
                            usuario.setImagen(tempFile.getFileName().toString());
                        }
                        usuario.setDescripcion(request.queryParams("descripcion"));
                        usuario.setEmail(request.queryParams("email"));

                    } else {
                        halt("Contraseña no coincide <a href=\"/editarcuenta\">Volver</a>");
                    }
                } else {
                    halt("El archivo no es una imagen <a href=\"/editarcuenta\">Volver</a>");
                }

                MantenimientoUsuario.getInstancia().editar(usuario);

                response.redirect("usuario/" + usuario.getUsername());
                attributes.put("usuario", usuario);

            }

            return new ModelAndView(attributes, "editarcuenta.ftl");
        }, freeMarkerEngine);

        get("/usuarios_registrados", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario usuarioSesion = request.session().attribute("usuario");
            List<Usuario> listaUsuarios = MantenimientoUsuario.getInstancia().findAll();
            attributes.put("usuarioSesion",usuarioSesion);
            attributes.put("usuarios", listaUsuarios);

            return new ModelAndView(attributes, "usuariosRegistrados.ftl");
        }, freeMarkerEngine);


        /**
         * Login
         */
        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            if (request.queryParams("r") != null) {
                attributes.put("message", "Estaba registrado y puede iniciar sesión ahora");
            }
            if (request.queryParams("err") != null) {
                attributes.put("error", "Credenciales no validas...");
            }

            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);

        post("/login", (request, response) -> {
            Map<String, Object> map = new HashMap<>();
            Session session = request.session(true);
            Usuario usuario = MantenimientoUsuario.getInstancia().find(request.queryParams("username"));

            if (usuario == null || !request.queryParams("password").equals(usuario.getPassword())) {
                response.redirect("/login?err=1");
                halt();
            } else {
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
            //TODO validar todos los campos
            Map<String, Object> attributes = new HashMap<>();
            String error = null;
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream is = request.raw().getPart("upfile").getInputStream()) {
                // Use the input stream to create a file
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);

                    if (!request.queryParams("username").equals(null)) {
                        String mimetype = request.raw().getPart("upfile").getContentType();
                        String type = mimetype.split("/")[0];
                        if (type.equals("image")) {
                            Usuario usuario = new Usuario();
                            usuario.setImagen(tempFile.getFileName().toString());
                            usuario.setPassword(request.queryParams("password"));
                            usuario.setUsername(request.queryParams("username"));
                            usuario.setDescripcion(request.queryParams("descripcion"));
                            usuario.setEmail(request.queryParams("email"));
                            MantenimientoUsuario.getInstancia().crear(usuario);
                            response.redirect("/login?r=1");
                            halt();
                        } else {
                            halt("El archivo no es una imagen");
                        }
                    } else {
                        error = "Error guardando";
                    }

            }


            attributes.put("error", error);
            attributes.put("username", request.queryParams("username"));
            attributes.put("email", request.queryParams("email"));
            return new ModelAndView(attributes, "singin.ftl");
        }, freeMarkerEngine);

        get("/crearpost", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("post", new Post("", "", null, null, null, null, 0));
            attributes.put("stringEtiquetas", "");

            return new ModelAndView(attributes, "crearPost.ftl");
        }, freeMarkerEngine);

        post("/crearpost", "multipart/form-data", (request, response) -> {


            //CODIGO PARA GUARDAR LA IMAGEN
            //- Servlet 3.x config
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream is = request.raw().getPart("upfile").getInputStream()) {
                // Use the input stream to create a file
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);

                String mimetype = request.raw().getPart("upfile").getContentType();
                String type = mimetype.split("/")[0];
                if (type.equals("image")) {
                    System.out.println("It's an image");
                    String etiquetasStr = request.queryParams("etiquetas");
                    String etiquetas[] = etiquetasStr.split("\\s*,\\s*");
                    Usuario usuario = request.session().attribute("usuario");
                    Post post = new Post();
                    post.setUsuario(usuario);
                    post.setCuerpo(request.queryParams("contenido"));
                    List<Etiqueta> listaEtiquetas = creacionEtiquetas(etiquetas);
                    post.setEtiquetas(listaEtiquetas);
                    post.setFecha(LocalDate.now());
                    post.setImagen(tempFile.getFileName().toString());

                    MantenimientoPost.getInstancia().crear(post);
                    response.redirect("/home");
                } else {
                    halt("El archivo no es una imagen");
                }
            }

            return "File uploaded";
        });

        post("/crearcomentario", (request, response) -> {


            Map<String, Object> attr = new HashMap<String, Object>();
            String comentarioStr = request.queryParams("comentario");

            Usuario usuario = request.session().attribute("usuario");
            System.out.println(usuario.getUsername());
            Post post = MantenimientoPost.getInstancia().find(Integer.parseInt(request.queryParams("id_post")));
            Comentario comentario = new Comentario(comentarioStr, usuario, post);
            MantenimientoComentario.getInstancia().crear(comentario);
            if (comentarioStr.length() == 0) {
                return "El comentario esta vacio";
            }

            response.redirect("/home");

            return "";
        });

        get("/editarpost/:id_post", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Editar articulo");

            int id_post = Integer.parseInt(request.params("id_post"));
            Post post = MantenimientoPost.getInstancia().find(id_post);
            String etiquetasstr = "";
            System.out.println(post.getEtiquetas().size());
            for (Etiqueta s : post.getEtiquetas()) {
                etiquetasstr += s.getEtiqueta() + ", ";
            }
            System.out.println(etiquetasstr);

            attributes.put("post", post);
            attributes.put("stringEtiquetas", etiquetasstr);


            return new ModelAndView(attributes, "editpost.ftl");
        }, freeMarkerEngine);


        post("/editarpost", (request, response) -> {

            String str = request.queryParams("etiquetas");
            String contenido = request.queryParams("contenido");
            String etiquetas[] = str.split("\\s*,\\s*");
            int id = Integer.parseInt(request.queryParams("id"));


            Post post = MantenimientoPost.getInstancia().find(id);
            post.setCuerpo(contenido);
            post.setEtiquetas(creacionEtiquetas(etiquetas));
            MantenimientoPost.getInstancia().editar(post);

            response.redirect("/home");
            return "";

        });

        get("/eliminarpost/:id_post", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            int id_post = Integer.parseInt(request.params("id_post"));
            Post post = MantenimientoPost.getInstancia().find(id_post);
            MantenimientoPost.getInstancia().eliminar(post);

            response.redirect("/home");

            return "";
        });
        get("/eliminarcomentario/:id_comentario", (request, response) -> {

            int id_comentario = Integer.parseInt(request.params("id_comentario"));
            Comentario comentario = MantenimientoComentario.getInstancia().find(id_comentario);
            MantenimientoComentario.getInstancia().eliminar(comentario);
            response.redirect("/home");


            return "";
        });


    }

    //se crean las etiquetas si es necesario y se entran en una lista para luego asignarles un post.
    public static List<Etiqueta> creacionEtiquetas(String[] etiquetas) {
        List<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
        for (String etiqueta : etiquetas) {
            try {
                Etiqueta etiquetaExistente = (Etiqueta) MantenimientoEtiqueta.getInstancia().getEntityManager().createQuery("SELECT E FROM Etiqueta E WHERE E.etiqueta='" + etiqueta + "'").getSingleResult();
                listaEtiquetas.add(etiquetaExistente);
            } catch (NoResultException e) {
                Etiqueta etiquetaNueva = new Etiqueta(etiqueta);
                MantenimientoEtiqueta.getInstancia().crear(etiquetaNueva);
                listaEtiquetas.add(etiquetaNueva);
            }

        }
        return listaEtiquetas;
    }
}
