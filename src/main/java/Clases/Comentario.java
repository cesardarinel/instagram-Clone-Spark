package Clases;

/**
 * Created by marti on 22/6/2016.
 */
public class Comentario {

    private long id;
    private String comentario;
    private Usuario usuario;
    private Post post;


    public Comentario() {
    }

    public Comentario(long id, String comentario, Usuario usuario, Post post) {
        this.id = id;
        this.comentario = comentario;
        this.usuario = usuario;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
