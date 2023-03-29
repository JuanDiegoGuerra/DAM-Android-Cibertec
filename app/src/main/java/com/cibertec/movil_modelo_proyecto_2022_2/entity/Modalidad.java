package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.io.Serializable;

public class Modalidad  implements Serializable {

    private int idModalidad;
    private String  descripcion;

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
