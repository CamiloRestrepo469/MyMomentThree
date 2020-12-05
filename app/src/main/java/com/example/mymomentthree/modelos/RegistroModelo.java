package com.example.mymomentthree.modelos;

import java.io.Serializable;

public class RegistroModelo implements Serializable {

    String id;
    String correo;
    String nombre;
    String cedula;
    String celular;

    @Override
    public String toString() {
        return "RegistroModelo{" +
                "id='" + id + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", celular='" + celular + '\'' +
                '}';
    }

    public RegistroModelo() {
    }

    public RegistroModelo(String id, String correo, String nombre, String cedula, String celular) {
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.cedula = cedula;
        this.celular = celular;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }


}
