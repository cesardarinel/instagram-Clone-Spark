package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by marti on 22/6/2016.
 */
public class Post {

    private long id;
    private String imagen;
    private LocalDate fecha;
    private String cuerpo;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Etiqueta> etiquetas;
    private Usuario usuario;


    public Post() {
    }

    public Post(long id, Usuario usuario, ArrayList<Etiqueta> etiquetas, ArrayList<Comentario> comentarios, String cuerpo, LocalDate fecha, String imagen) {
        this.id = id;
        this.usuario = usuario;
        this.etiquetas = etiquetas;
        this.comentarios = comentarios;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.imagen = imagen;
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

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public ArrayList<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(ArrayList<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
