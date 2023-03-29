package com.cibertec.movil_modelo_proyecto_2022_2.entity;

import java.io.Serializable;

    public class Proveedor implements Serializable {
        private int idProveedor;
        private String razonsocial;
        private String ruc;
        private String direccion;
        private String telefono;
        private String celular;
        private String contacto;
        private int estado;
        private String fechaRegistro;
        private Pais pais;

        public int getIdProveedor() {
            return idProveedor;
        }

        public void setIdProveedor(int idProveedor) {
            this.idProveedor = idProveedor;
        }

        public String getRazonsocial() {
            return razonsocial;
        }

        public void setRazonsocial(String razonsocial) {
            this.razonsocial = razonsocial;
        }

        public String getRuc() {
            return ruc;
        }

        public void setRuc(String ruc) {
            this.ruc = ruc;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getCelular() {
            return celular;
        }

        public void setCelular(String celular) {
            this.celular = celular;
        }

        public String getContacto() {
            return contacto;
        }

        public void setContacto(String contacto) {
            this.contacto = contacto;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }

        public String getFechaRegistro() {
            return fechaRegistro;
        }

        public void setFechaRegistro(String fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
        }

        public Pais getPais() {
            return pais;
        }

        public void setPais(Pais pais) {
            this.pais = pais;
        }
    }

