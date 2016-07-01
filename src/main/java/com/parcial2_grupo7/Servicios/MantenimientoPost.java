package com.parcial2_grupo7.Servicios;

import com.parcial2_grupo7.Clases.Post;

/**
 * Created by marti on 30/6/2016.
 */
public class MantenimientoPost extends GestionDB<Post>{
    private static MantenimientoPost instancia;

    private MantenimientoPost() {
        super(Post.class);
    }

    public static MantenimientoPost getInstancia(){
        if(instancia==null){
            instancia = new MantenimientoPost();
        }
        return instancia;
    }

}
