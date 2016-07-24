package com.parcial2_grupo7.Clases;

import com.google.gson.annotations.Expose;
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
public class Usuario implements Serializable {

    @Id
    @Column(name = "USERNAME")
    @Expose
    private String username;
    @Column(name = "IMAGEN", length = 10000000)
    private String imagen;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(mappedBy = "likes",fetch = FetchType.EAGER)
    private List<Post> liked = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Comentario> comentarios = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade ={CascadeType.DETACH})
    @JoinTable(name="USER_FOLLOWERS",
            joinColumns={@JoinColumn(name="USERNAME")},
            inverseJoinColumns={@JoinColumn(name="FOLLOWER_ID")})
    private List<Usuario> followers = new ArrayList<Usuario>();

    @ManyToMany(mappedBy="followers", fetch = FetchType.EAGER)
    private List<Usuario> following = new ArrayList<Usuario>();


    public Usuario() {
    }

    public Usuario(String username, String imagen, String email, String password, String descripcion, List<Post> posts, List<Post> liked, List<Comentario> comentarios, List<Usuario> followers, List<Usuario> following) {
        this.username = username;
        this.imagen = imagen;
        this.email = email;
        this.password = password;
        this.descripcion = descripcion;
        this.posts = posts;
        this.liked = liked;
        this.comentarios = comentarios;
        this.followers = followers;
        this.following = following;
    }

    public List<Post> getLiked() {
        return liked;
    }

    public void setLiked(List<Post> liked) {
        this.liked = liked;
    }

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

