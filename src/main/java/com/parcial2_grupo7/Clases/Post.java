package com.parcial2_grupo7.Clases;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by marti on 22/6/2016.
 */
@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String imagen;
    private LocalDate fecha;
    private String cuerpo;
    @OneToMany
    private Set<Comentario> comentarios;
    @OneToMany
    private Set<Etiqueta> etiquetas;
    @ManyToOne
    private Usuario usuario;


    public Post() {
    }

    public Post(String imagen, LocalDate fecha, String cuerpo, Set<Comentario> comentarios, Set<Etiqueta> etiquetas, Usuario usuario) {
        this.imagen = imagen;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.comentarios = comentarios;
        this.etiquetas = etiquetas;
        this.usuario = usuario;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
