package com.palazzisoft.ligabalonpie.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipo implements Serializable {

    private Integer id;
    private String nombre;
    private List<Jugador> jugadores;
    private Participante participante;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void addJugadores(Jugador... jugadoresLista) {
        if (jugadores == null) {
            jugadores = new ArrayList<>();
        }

        jugadores.addAll(Arrays.asList(jugadoresLista));
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
