package com.parcial2_grupo7.main;

import com.parcial2_grupo7.Clases.Usuario;
import com.parcial2_grupo7.Servicios.BaseDatos;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;



import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by cesar on 22/06/16.
 */
public class Main {
    // copiado del base de la pagina funciona genial
    private static EntityManagerFactory emf;
    private Class<Usuario> usuarioClass;


    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    /**
     *
     * @param entidad
     */
    public static void Crear(Usuario entidad){

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(entidad);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {


        staticFileLocation("/Recursos");
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/template");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
        try {
            BaseDatos.startDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BaseDatos ba = new BaseDatos();
        ba.getConexion();
        ba.testConexion();
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        }
        Usuario prueba=new Usuario();
        prueba.setUsername("cesar");
        prueba.setPassword("123456");
        Crear(prueba);

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
