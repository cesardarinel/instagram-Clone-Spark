package com.parcial2_grupo7.Clases;

/**
 * Created by marti on 22/6/2016.
 */
public class Etiqueta {

    private long id;
    private String tag;

    public Etiqueta() {
    }

    public Etiqueta(long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
