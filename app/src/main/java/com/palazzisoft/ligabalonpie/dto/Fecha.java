package com.palazzisoft.ligabalonpie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fecha implements Serializable{

    private Integer id;
    private Integer numero;
    private List<Partido> partidos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public boolean estaJugada() {
        boolean estaJugada = true;
        for (Partido partido : partidos) {
            estaJugada = estaJugada && partido.isJugado();
        }

        return estaJugada;
    }
}
