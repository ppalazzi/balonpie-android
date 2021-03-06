package com.palazzisoft.ligabalonpie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Partido implements Serializable{

    public static final String NO_JUGADO = "-";
    private Integer id;
    private Equipo local;
    private Equipo visitante;
    private Integer golesLocal;
    private Integer golesVisitante;
    private boolean jugado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Equipo getLocal() {
        return local;
    }

    public void setLocal(Equipo local) {
        this.local = local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public void setVisitante(Equipo visitante) {
        this.visitante = visitante;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public boolean isJugado() {
        return jugado;
    }

    public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }

    public String mostrarGolesVisitante() {
        if (isJugado()) {
            return golesVisitante.toString();
        }

        return NO_JUGADO;
    }

    public String mostrarGolesLocal() {
        if (isJugado()) {
            return golesLocal.toString();
        }

        return NO_JUGADO;
    }
}
