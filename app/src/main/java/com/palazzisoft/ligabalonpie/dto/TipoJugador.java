package com.palazzisoft.ligabalonpie.dto;

import java.io.Serializable;

public class TipoJugador implements Serializable {

    public static final int ARQUERO = 0;
    public static final int DEFENSOR = 1;
    public static final int MEDIOCAMPISTA = 2;
    public static final int ATACANTE = 3;

    private int id;
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
