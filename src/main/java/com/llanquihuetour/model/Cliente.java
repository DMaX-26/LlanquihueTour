package com.llanquihuetour.model;

import com.llanquihuetour.exception.EdadInvalidaException;

/**
 * Subclase que representa a un Cliente
 * Hereda atributos, métodos e implementaciones de la superclase "Persona"
 * Implementa la interfaz Registrable
 */
public class Cliente extends Persona {
    /**
     * Atributos propios
     * Cliente tiene un GuiaTuristico
     */
    private int edad;
    private String nacionalidad;
    private GuiaTuristico guiaTuristico;

    /**
     * Constructor para crear objetos e inicializar atributos heredados y propios
     * @param nombre
     * @param rut
     * @param correo
     * @param telefono
     * @param edad
     * @param nacionalidad
     * @param guiaTuristico
     */
    public Cliente(String nombre, Rut rut, String correo, String telefono, int edad, String nacionalidad, GuiaTuristico guiaTuristico) throws EdadInvalidaException {
        super(nombre, rut, correo, telefono);
        /**
         * Validamos que la edad sea correcta antes de inicializar el atributo
         */
        if (edad < 0){
            throw new EdadInvalidaException("La edad ingresada no es válida");
        }
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.guiaTuristico = guiaTuristico;
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

    public String getNacionalidad() {
        return nacionalidad;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public GuiaTuristico getGuiaTuristico() {
        return guiaTuristico;
    }
    public void setGuiaTuristico(GuiaTuristico guiaTuristico) {
        this.guiaTuristico = guiaTuristico;
    }

    /**
     * Conversión de objeto a texto
     * Se reutiliza el metodo toString de la superclase "Persona" y se agrega información de la clase Cliente
     * @return
     */
    @Override
    public String toString() {
        return super.toString()+", Edad: "+edad+", Nacionalidad: "+nacionalidad+", Guía Turístico: "+guiaTuristico;
    }

    /**
     * Se implementa el metodo registrar correspondiente a la interfaz Registrable
     */
    @Override
    public void registrar() {
        System.out.println();
        System.out.println(">>> \033[32mEl Cliente "+"'"+getNombre()+"'"+", ha sido registrado correctamente\u001B[0m <<<");
        System.out.println();
    }

    /**
     * Se implementa el metodo mostrarDatos correspondiente a la interfaz Registrable
     */
    @Override
    public void mostrarDatos() {
        System.out.println("\033[33m:::Cliente:::\u001B[0m");
        super.mostrarDatos();
        System.out.println("Edad: "+edad);
        System.out.println("Nacionalidad: "+nacionalidad);
        System.out.println("Guía Turístico Asignado: "+guiaTuristico);
    }
}
