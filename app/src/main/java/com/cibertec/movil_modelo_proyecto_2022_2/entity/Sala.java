package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.io.Serializable;

public class Sala implements Serializable {

    private int idSala;
    private String numero;
    private int piso;
    private int capacidad;
    private String recursos;
    private String fechaSeparacion;
    private String fechaRegistro;
    private int estado;
    //private int sede;

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public String getFechaSeparacion() {
        return fechaSeparacion;
    }

    public void setFechaSeparacion(String fechaSeparacion) { this.fechaSeparacion = fechaSeparacion;  }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

/*    public int getSede() {
        return sede;
    }

    public void setSede(int sede) {
        this.sede = sede;
    } */
}

