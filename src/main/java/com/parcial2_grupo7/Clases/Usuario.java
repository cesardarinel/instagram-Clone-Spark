package com.parcial2_grupo7.Clases;

import com.parcial2_grupo7.Servicios.MantenimientoUsuario;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by marti on 22/6/2016.
 */
@Entity
@Table (name = "USUARIOS")
public class Usuario {

    @Id
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "IMAGEN")
    private String imagen;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Comentario> comentarios = new ArrayList<>();


    @ManyToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="USER_FOLLOWERS",
            joinColumns={@JoinColumn(name="USERNAME")},
            inverseJoinColumns={@JoinColumn(name="FOLLOWER_ID")})
    private List<Usuario> followers = new ArrayList<Usuario>();

    @ManyToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="USER_FOLLOWINGS",
            joinColumns={@JoinColumn(name="USERNAME")},
            inverseJoinColumns={@JoinColumn(name="FOLLOWING_ID")})
    private List<Usuario> following = new ArrayList<Usuario>();


    public Usuario() {
    }

    public Usuario( String imagen, String username, String email, String password, String descripcion, List<Post> posts, List<Comentario> comentarios) {
        this.imagen = imagen;
        this.username = username;
        this.email = email;
        this.password = password;
        this.descripcion = descripcion;
        this.posts = posts;
        this.comentarios = comentarios;
    }

    public Usuario( String imagen, String username) {
        this.imagen = imagen;
        this.username = username;
    }


    /*public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }*/

    public List<Usuario> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Usuario> followers) {
        this.followers = followers;
    }

    public List<Usuario> getFollowing() {
        //EntityManager em = MantenimientoUsuario.getInstancia().getEntityManager();
        //Set<Usuario> following2 = (Set<Usuario>) em.createQuery("SELECT U FROM USER_FOLLOWERS WHERE FOLLOWER_ID = "+this.getUsername());
        return following;
    }

    public void setFollowing(List<Usuario> following) {
        this.following = following;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }


}

