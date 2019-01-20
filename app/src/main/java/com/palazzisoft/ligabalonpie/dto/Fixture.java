package com.palazzisoft.ligabalonpie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fixture implements Serializable {

    private Integer id;
    private List<Fecha> fechas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Fecha> getFechas() {
        return fechas;
    }

    public void setFechas(List<Fecha> fechas) {
        this.fechas = fechas;
    }
}
