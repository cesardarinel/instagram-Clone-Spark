package com.parcial2_grupo7.Clases;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marti on 22/6/2016.
 */
@Entity
@Table (name = "COMENTARIOS")
public class Comentario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private int id;
    @Column (name = "COMENTARIO")
    private String comentario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POST_ID")
    private Post post;


    public Comentario() {
    }

    public Comentario(String comentario, Usuario usuario, Post post) {
        this.comentario = comentario;
        this.usuario = usuario;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
