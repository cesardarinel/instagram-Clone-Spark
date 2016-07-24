package com.parcial2_grupo7.Servicios;

import com.parcial2_grupo7.Clases.Post;
import com.parcial2_grupo7.Clases.Usuario;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by marti on 24/7/2016.
 */
public class ClientService {
    public List<Post> getAllPost(){
        return MantenimientoPost.getInstancia().findAll();
    }

    public List<Usuario> getAllUsers(){
        return  MantenimientoUsuario.getInstancia().findAll();
    }

    public  List<Post> getPostsByUser(String username){
        Usuario usuario = MantenimientoUsuario.getInstancia().find(username);
        return usuario.getPosts();
    }

    public Post setPost(String username, String imagen, String cuerpo){
        Usuario usuario = MantenimientoUsuario.getInstancia().find(username);
        Post post = new Post();
        post.setUsuario(usuario);
        post.setImagen(imagen);
        post.setCuerpo(cuerpo);
        post.setFecha(LocalDate.now());
        MantenimientoPost.getInstancia().crear(post);
        return post;
    }
}
