package com.palazzisoft.ligabalonpie.dto;


public class EquiposEstadisticas {

    private Equipo equipo;
    private int puntos;
    private int golesAFavor;
    private int golesRecibidos;
    private int cantidadJugados;

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGolesAFavor() {
        return golesAFavor;
    }

    public void setGolesAFavor(int golesAFavor) {
        this.golesAFavor = golesAFavor;
    }

    public int getGolesRecibidos() {
        return golesRecibidos;
    }

    public void setGolesRecibidos(int golesRecibidos) {
        this.golesRecibidos = golesRecibidos;
    }

    public int getCantidadJugados() {
        return cantidadJugados;
    }

    public void setCantidadJugados(int cantidadJugados) {
        this.cantidadJugados = cantidadJugados;
    }
}
