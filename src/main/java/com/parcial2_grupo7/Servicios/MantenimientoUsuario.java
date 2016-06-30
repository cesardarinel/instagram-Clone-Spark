package com.parcial2_grupo7.Servicios;

import com.parcial2_grupo7.Clases.Usuario;

import javax.persistence.EntityManager;

/**
 * Created by cesar on 29/06/16.
 */
public class MantenimientoUsuario extends GestionDB<Usuario> {

private static MantenimientoUsuario instancia;

        private MantenimientoUsuario() {
            super(Usuario.class);
        }

        public static MantenimientoUsuario getInstancia(){
            if(instancia==null){
            instancia = new MantenimientoUsuario();
            }
            return instancia;
        }

}

