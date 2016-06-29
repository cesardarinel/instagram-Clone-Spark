package com.parcial2_grupo7.Clases;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

/**
 * Created by marti on 22/6/2016.
 */
@Entity
public class Usuario {
    @Id
    private long id;
    private String imagen;
    private String username;
    private String email;
    private String password;
    private String descripcion;
    private Boolean tipoCuenta;
    private ArrayList<Post> posts;

 //   hay que definir el modelo, es uno a mucho ?

    public Usuario() {
    }

    public Usuario(long id, String imagen, String username, String email, String password, String descripcion, Boolean tipoCuenta, ArrayList<Post> posts) {
        this.id = id;
        this.imagen = imagen;
        this.username = username;
        this.email = email;
        this.password = password;
        this.descripcion = descripcion;
        this.tipoCuenta = tipoCuenta;
        this.posts = posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public Boolean getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(Boolean tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}

