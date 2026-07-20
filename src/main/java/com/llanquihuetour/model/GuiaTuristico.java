package com.llanquihuetour.model;

import com.llanquihuetour.interfaces.Registrable;

/**
 * Subclase que representa a un Guía turístico
 * Hereda atributos y métodos de la superclase "Persona"
 * Implementa la interfaz Registrable
 */
public class GuiaTuristico extends Persona implements Registrable {
    /**
     * Atributos propios
     */
    private int edad;
    private String idiomas;

    /**
     * Constructor para crear los objetos e inicializar atributos heredados y propios
     * @param nombre
     * @param rut
     * @param correo
     * @param telefono
     * @param edad
     * @param idiomas
     */
    public GuiaTuristico(String nombre, Rut rut, String correo, String telefono, int edad, String idiomas) {
        super(nombre, rut, correo, telefono);
        this.edad = edad;
        this.idiomas = idiomas;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getIdiomas() {
        return idiomas;
    }
    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public String toString() {
        return super.toString()+", Edad: "+edad+", Idiomas: "+idiomas;
    }

    /**
     * Se implementa el metodo registrar correspondiente a la interfaz Registrable
     */
    @Override
    public void registrar() {
        System.out.println();
        System.out.println(">>> \033[32mEl Guía turístico "+"'"+getNombre()+"'"+", ha sido registrado correctamente\u001B[0m <<<");
        System.out.println();
    }

    /**
     * Se implementa el metodo mostrarDatos correspondiente a la interfaz Registrable
     */
    @Override
    public void mostrarDatos() {
        System.out.println("\033[36m---Guía Turístico---\u001B[0m");
        super.mostrarDatos();
        System.out.println("Edad: "+edad);
        System.out.println("Idiomas: "+idiomas);
    }
}
