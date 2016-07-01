package com.parcial2_grupo7.Servicios;

import com.parcial2_grupo7.Clases.Etiqueta;
import com.parcial2_grupo7.Clases.Post;

/**
 * Created by marti on 1/7/2016.
 */
public class MantenimientoEtiqueta extends GestionDB<Etiqueta> {
    private static MantenimientoEtiqueta instancia;

    private MantenimientoEtiqueta() {
        super(Etiqueta.class);
    }

    public static MantenimientoEtiqueta getInstancia(){
        if(instancia==null){
            instancia = new MantenimientoEtiqueta();
        }
        return instancia;
    }
}
