package com.parcial2_grupo7.Servicios;

import com.parcial2_grupo7.Clases.Comentario;
import com.parcial2_grupo7.Clases.Etiqueta;

/**
 * Created by marti on 1/7/2016.
 */
public class MantenimientoComentario  extends GestionDB<Comentario> {
    private static MantenimientoComentario instancia;

    private MantenimientoComentario() {
        super(Comentario.class);
    }

    public static MantenimientoComentario getInstancia(){
        if(instancia==null){
            instancia = new MantenimientoComentario();
        }
        return instancia;
    }
}
