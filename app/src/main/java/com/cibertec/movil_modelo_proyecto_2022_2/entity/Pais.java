package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.io.Serializable;

public class Pais implements Serializable {
    private int idPais;
    private String iso;
    private String nombre;

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
