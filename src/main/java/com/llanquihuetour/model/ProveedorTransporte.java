package com.llanquihuetour.model;

import com.llanquihuetour.interfaces.Registrable;

/**
 * Subclase Clase que representa a un Proveedor de transporte
 * Hereda atributos y métodos de la superclase "Persona"
 */
public class ProveedorTransporte extends Persona implements Registrable {
    /**
     * Atributos propios
     * ProveedorTransporte tiene una dirección
     */
    private String tipoTransporte;
    private Direccion direccion;

    /**
     * Constructor para crear los objetos e inicializar atributos heredados y propios
     * @param nombre
     * @param rut
     * @param correo
     * @param telefono
     * @param tipoTransporte
     * @param direccion
     */
    public ProveedorTransporte(String nombre, Rut rut, String correo, String telefono, String tipoTransporte, Direccion direccion) {
        /**
         * Llama al constructor de la clase padre para inicializar nombre, rut, correo y telefono en el objeto "ProveedorTransporte"
         */
        super(nombre, rut, correo, telefono);
        /**
         * Se inicializan los atributos propios
         */
        this.tipoTransporte = tipoTransporte;
        this.direccion = direccion;
    }

    public String getTipoTransporte() {
        return tipoTransporte;
    }
    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Conversión de objeto a texto
     * Sobreescribe el metodo toString de la superclase "Persona" y agrega información propia de ProveedorTransporte
     * @return
     */
    @Override
    public String toString() {
        return super.toString()+", Tipo de Transporte: "+tipoTransporte+", Dirección: "+direccion;
    }

    /**
     * Se implementa el metodo registrar correspondiente a la interfaz Registrable
     */
    @Override
    public void registrar() {
        System.out.println();
        System.out.println(">>> \033[32mEl Proveedor de transporte "+"'"+getNombre()+"'"+", ha sido registrado correctamente\u001B[0m <<<");
        System.out.println();
    }

    /**
     * Se implementa el metodo mostrarDatos correspondiente a la interfaz Registrable
     */
    @Override
    public void mostrarDatos() {
        System.out.println("\033[34m***Proveedor de transporte***\u001B[0m");
        super.mostrarDatos();
        System.out.println("Tipo de transporte: "+tipoTransporte);
        System.out.println(direccion);
    }
}
