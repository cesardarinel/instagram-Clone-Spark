package com.parcial2_grupo7.Servicios;



import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by cesar on 28/06/16.
 */
public class BaseDatos {



    private static BaseDatos instancia;
    private String URL = "jdbc:h2:tcp://localhost/~/pruebaTep";

    /**
     *Implementando el patron Singlenton
     */
    public BaseDatos(){
        registrarDriver();
    }

    /**
     * Retornando la instancia.
     * @return
     */
    public static BaseDatos getInstancia(){
        if(instancia==null){
            instancia = new BaseDatos();
        }
        return instancia;
    }

    /**
     * Metodo para el registro de driver de conexión.
     */
    private void registrarDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            //error
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {
           // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            //Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        /**
         *
         * @throws SQLException
         */
        public static void startDb() throws SQLException {
            Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        }

        /**
         *
         * @throws SQLException
         */
        public static void stopDb() throws SQLException {
            Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
        }



}

