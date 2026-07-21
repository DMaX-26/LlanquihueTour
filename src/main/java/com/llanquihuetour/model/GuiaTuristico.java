package com.llanquihuetour.model;

import com.llanquihuetour.exception.EdadInvalidaException;

/**
 * Subclase que representa a un Guía turístico
 * Hereda atributos, métodos e implementaciones de la superclase "Persona"
 * Implementa la interfaz Registrable
 */
public class GuiaTuristico extends Persona {
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
    public GuiaTuristico(String nombre, Rut rut, String correo, String telefono, int edad, String idiomas) throws EdadInvalidaException {
        super(nombre, rut, correo, telefono);

        /**
         * Si la edad del GuiaTuristico es menor que 0, se lanza una excepción personalizada
         */
        if (edad<0){
            throw new EdadInvalidaException("La edad ingresada no es válida");
        }
        this.edad = edad;
        this.idiomas = idiomas;
    }

    /**
     * Métodos getter and setter
     * @return
     */
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
