package com.llanquihuetour.model;

import com.llanquihuetour.interfaces.Registrable;

public abstract class Persona implements Registrable {
    private String nombre;
    private Rut rut;
    private String correo;
    private String telefono;

    public Persona(String nombre, Rut rut, String correo, String telefono) {
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rut getRut() {
        return rut;
    }
    public void setRut(Rut rut) {
        this.rut = rut;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " +nombre+", Rut: "+rut+", Correo: "+correo+", Teléfono: "+telefono;
    }

    @Override
    public void registrar() {
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Nombre: "+nombre);
        System.out.println("Rut: "+rut);
        System.out.println("Correo: "+correo);
        System.out.println("Teléfono: "+telefono);
    }
}
