package com.llanquihuetour.model;

import com.llanquihuetour.interfaces.Registrable;

/**
 * Subclase que representa a un Proveedor de alojamiento
 * Hereda atributos y métodos de la superclase Persona
 */
public class ProveedorAlojamiento extends Persona implements Registrable {
    /**
     * Atributos propios
     */
    private double precioPorNoche;
    private Direccion direccion;

    /**
     * Constructor para crear los objetos e inicializar atributos heredados y propios
     * @param nombre
     * @param rut
     * @param correo
     * @param telefono
     * @param precioPorNoche
     * @param direccion
     */
    public ProveedorAlojamiento(String nombre, Rut rut, String correo, String telefono, double precioPorNoche, Direccion direccion) {
        super(nombre, rut, correo, telefono);
        this.precioPorNoche = precioPorNoche;
        this.direccion = direccion;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Conversión de objeto a texto
     * Se sobreescribe el metodo toString de la superclase "Persona" y se agrega información propia de la clase ProveedorAlojamiento
     * @return
     */
    @Override
    public String toString() {
        return super.toString()+", Precio por noche: "+precioPorNoche+", Dirección: "+direccion;
    }

    /**
     * Se implementa el metodo registrar correspondiente a la interfaz Registrable
     */
    @Override
    public void registrar() {
        System.out.println();
        System.out.println(">>> \033[32mEl Proveedor de alojamiento "+"'"+getNombre()+"'"+", ha sido registrado correctamente\u001B[0m <<<");
        System.out.println();
    }

    /**
     * Se implementa el metodo mostrarDatos correspondiente a la interfaz Registrable
     */
    @Override
    public void mostrarDatos() {
        System.out.println("\033[34m===Proveedor de Alojamiento===\u001B[0m");
        super.mostrarDatos();
        System.out.println("Precio por noche: "+precioPorNoche);
        System.out.println(direccion);
    }
}
